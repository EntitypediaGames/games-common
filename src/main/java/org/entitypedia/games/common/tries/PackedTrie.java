package org.entitypedia.games.common.tries;

import org.entitypedia.games.common.buffer.BufferFacade;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.util.*;

/**
 * Packed Trie implementation inspired by
 * <p/>
 * Ulrich Germann, Eric Joanis, Samuel Larkin (2009).
 * "Tightly packed tries: how to fit large models into memory, and make them load fast, too" .
 * http://www.aclweb.org/anthology/W/W09/W09-1505.pdf
 * ACL Workshops: Proceedings of the Workshop on Software Engineering, Testing, and Quality Assurance for Natural Language Processing.
 * Association for Computational Linguistics. pp. 31–39.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class PackedTrie {

    private static final int MAX_CHILDREN = 256;
    // max 20 bytes per child = two variable length longs, max 10 bytes each
    private final ByteArrayOutputStream childrenStream = new ByteArrayOutputStream(20 * MAX_CHILDREN);

    // for reading
    private BufferFacade buffer;
    private char[] rootChars = new char[0];
    private long[] rootOffsets = new long[0];
    private boolean[] rootIsWord = new boolean[0];
    private boolean[] rootHasChildren = new boolean[0];
    private long[] rootValues = new long[0];

    public PackedTrie() {
    }

    public PackedTrie(BufferFacade b) throws IOException {
        this.buffer = b;

        // load root offset from the end of the buffer
        long rootOffset = readVarLenLong1Back(buffer, buffer.limit() - 1, -1);

        // read root index and init arrays.
        long nodeValue = readVarLenLong01(buffer, rootOffset);
        long offset = rootOffset + getVarLenLongSize(nodeValue);
        boolean hasChildren = 0 < (nodeValue & 0x2L);
        //boolean isWord = 0 < (nodeValue & 0x1L);
        //nodeValue = nodeValue >> 2;

        Map<Character, Long> rootsMap = new TreeMap<Character, Long>();

        if (hasChildren) {
            long sizeOfIndex = readVarLenLong01(buffer, offset);
            offset = offset + getVarLenLongSize(sizeOfIndex);

            long endOfIndex = offset + sizeOfIndex;
            while (offset < endOfIndex) {
                long indexKey = readVarLenLong1(buffer, offset, endOfIndex);
                offset = offset + getVarLenLongSize(indexKey);
                long relOffset = readVarLenLong0(buffer, offset, endOfIndex);
                offset = offset + getVarLenLongSize(relOffset);
                rootsMap.put((char) indexKey, rootOffset - relOffset);
            }

            rootChars = new char[rootsMap.size()];
            rootOffsets = new long[rootsMap.size()];
            rootIsWord = new boolean[rootsMap.size()];
            rootHasChildren = new boolean[rootsMap.size()];
            rootValues = new long[rootsMap.size()];
            int i = 0;
            for (Map.Entry<Character, Long> entry : rootsMap.entrySet()) {
                rootChars[i] = entry.getKey();
                rootOffsets[i] = entry.getValue();

                // read flags and values
                nodeValue = readVarLenLong01(buffer, rootOffsets[i]);
                rootHasChildren[i] = 0 < (nodeValue & 0x2L);
                rootIsWord[i] = 0 < (nodeValue & 0x1L);
                rootValues[i] = nodeValue >> 2;

                i++;
            }
        }
    }

    /**
     * Packs the {@code trie} into writable {@code out} stream.
     *
     * @param trie input trie
     * @param out  output stream
     */
    public void pack(BasicTrie trie, OutputStream out) throws IOException {
        BasicTrieNode root = trie.getRoot();
        long rootOffset = 0;
        long offset = 0;

        Deque<BasicTrieNode> q = new ArrayDeque<BasicTrieNode>();
        BasicTrieNode prevNode = null;

        // iterative post-order
        q.addFirst(trie.getRoot());
        while (!q.isEmpty()) {
            BasicTrieNode curNode = q.peekFirst();

            if (null == prevNode || prevNode.getChildren().contains(curNode)) {// going down
                if (0 < curNode.getChildren().size()) {
                    q.addFirst(curNode.getChildren().iterator().next());
                }
            } else { // going up
                // curNode == prevNode => we hit the bottom, nothing to add => visit
                // if truly up, iterate and find prevNode among children.
                // if there any other children left unvisited => add them to the queue
                Iterator<BasicTrieNode> i = curNode.getChildren().iterator();
                //noinspection StatementWithEmptyBody
                while (i.hasNext() && i.next() != prevNode) {
                }
                if (i.hasNext()) {
                    q.addFirst(i.next());
                } else {
                    // visit node
                    curNode.setOffset(offset);
                    if (curNode == root) {
                        rootOffset = offset;
                    }
                    offset = offset + writeNode(curNode, out);

                    q.removeFirst();
                }
            }
            prevNode = curNode;
        }

        writeVarLenLong1(rootOffset, out);
    }

    /**
     * Writes the {@code node} into {@code out} stream and returns the number of bytes written.
     *
     * @param node node to write
     * @param out  stream to write to
     * @return number of bytes written
     */
    private long writeNode(BasicTrieNode node, OutputStream out) throws IOException {
//        0  13 offset of root node                             // save root offset at the end in MSB 1 bytes (root should have children and the last has offset in MSB 0 bytes
//    ____
//        1  10 node value of ‘aa’                               // word id, variable length long, MSB 01 bytes + 2 LSB bits=hasChildren+isWord flags. max id 2^61-1
//        2  0 size of index to child nodes of ‘aa’ in bytes     // index size, variable length long, MSB 01 bytes, absent if !hasChildren
//    ____
//        3  3 node value of ‘ab’
//        4  0 size of index to child nodes of ‘ab’ in bytes
//    ____
//        5  13 node value of ‘a’
//        6  4 size of index to child nodes of ‘a’ in bytes
//        7  a index key for ‘aa’ coming from ‘a’                // char -> variable length long, MSB 1 bytes. ASCII wins, for the rest an encoding to fit most chars in 0-127 (or frequency-based one) would be better.
//        8  4 relative offset of node ‘aa’ (5 − 4 = 1)         // long -> variable length long, MSB 0 bytes
//        9  b index key for ‘ab’ coming from ‘a’
//        10 2 relative offset of node ‘ab’ (5 − 2 = 3)
//    ____
//        11 7 node value of ‘b’
//        12 0 size of index to child nodes of ‘b’ in bytes
//    ____
//        13 20 root node value
//        14 4 size of index to child nodes of root in bytes
//        15 a index key for ‘a’ coming from root
//        16 8 relative offset of node ‘a’ (13 − 8 = 5)
//        17 b index key for ‘b’ coming from root
//        18 2 relative offset of node ‘b’ (13 − 2 = 11)

        long result = 0;

        // node value = word id + flags

        long nodeValue = node.getId();
        // bounds check -> max id 2^61-1 - need two bits for flags
        if ((0x3FFFFFFFFFFFFFFFL - 1) < nodeValue) {
            throw new IndexOutOfBoundsException();
        }

        // add flags
        // 2 LSB bits=hasChildren+isWord flags.
        nodeValue = nodeValue << 2;
        if (!node.getChildren().isEmpty()) {
            nodeValue = nodeValue | 0x2L;
        }
        if (node.isWord()) {
            nodeValue = nodeValue | 0x1L;
        }

        result = result + getVarLenLongSize(nodeValue);
        writeVarLenLong01(out, nodeValue);

        if (!node.getChildren().isEmpty()) {
            ByteArrayOutputStream cStream = childrenStream;
            if (MAX_CHILDREN < node.getChildren().size()) {
                cStream = new ByteArrayOutputStream(20 * node.getChildren().size());
            } else {
                cStream.reset();
            }
            // children should be sorted!
            for (BasicTrieNode child : node.getChildren()) {
                // index key
                long indexKey = child.getChar();
                writeVarLenLong1(indexKey, cStream);

                // relative offset
                long relOffset = node.getOffset() - child.getOffset();
                writeVarLenLong0(relOffset, cStream);
            }

            long sizeOfIndex = cStream.size();
            result = result + getVarLenLongSize(sizeOfIndex);
            writeVarLenLong01(out, sizeOfIndex);

            result = result + cStream.size();
            cStream.writeTo(out);
        }

        return result;
    }

    /**
     * Returns the size in bytes of the variable length encoded 64-bit integer.
     *
     * @param value 64-bit integer to encode
     * @return size in bytes of the variable length encoded 64-bit integer
     */
    private int getVarLenLongSize(final long value) {
        if ((value & (0xFFFFFFFFFFFFFFFFL << 7)) == 0) return 1;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 14)) == 0) return 2;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 21)) == 0) return 3;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 28)) == 0) return 4;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 35)) == 0) return 5;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 42)) == 0) return 6;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 49)) == 0) return 7;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 56)) == 0) return 8;
        if ((value & (0xFFFFFFFFFFFFFFFFL << 63)) == 0) return 9;
        return 10;
    }

    /**
     * Encodes 64-bit integer as series of MSB0 bytes with last MSB1 byte.
     *
     * @param value 64-bit integer to encode
     * @throws IOException
     */
    private void writeVarLenLong01(OutputStream out, long value) throws IOException {
        while (true) {
            if ((value & ~0x7FL) == 0) {
                out.write(((int) value & 0x7F) | 0x80);
                return;
            } else {
                out.write((int) value & 0x7F);
                value = value >>> 7;
            }
        }
    }

    /**
     * Reads 64-bit integer encoded as series of MSB0 bytes with the last MSB1 byte.
     *
     * @param buffer buffer where to read from
     * @param offset where to start reading
     * @return 64-bit integer
     * @throws IOException
     */
    private long readVarLenLong01(BufferFacade buffer, long offset) throws IOException {
        int shift = 0;
        long result = 0;
        while (shift <= 70 && offset < buffer.limit()) {
            final byte b = buffer.get(offset);
            if ((b & 0x80) == 0x80) {
                result = result | ((long) (b & 0x7F) << shift);
                return result;
            }
            result = result | ((long) (b) << shift);
            shift = shift + 7;
            offset = offset + 1;
        }
        if (offset != buffer.limit() || shift == 77) {
            throw new InvalidObjectException("Malformed variable length long");
        } else {
            return result;
        }
    }

    /**
     * Writes 64-bit integer encoded as series of MSB0 bytes with the last MSB1 byte.
     *
     * @param out stream to write to
     * @throws IOException
     */
    private void writeVarLenLong1(long value, OutputStream out) throws IOException {
        while (true) {
            if ((value & ~0x7FL) == 0) {
                out.write(((int) value & 0x7F) | 0x80);
                return;
            } else {
                out.write(((int) value & 0x7F) | 0x80);
                value = value >>> 7;
            }
        }
    }

    /**
     * Reads 64-bit integer encoded as series of MSB1 bytes.
     *
     * @param buffer  buffer where to read from
     * @param offset  where to start reading
     * @param ceiling where to stop reading, exclusive
     * @return 64-bit integer
     * @throws IOException
     */
    private long readVarLenLong1(BufferFacade buffer, long offset, long ceiling) throws IOException {
        int shift = 0;
        long result = 0;
        while (shift <= 70 && offset < ceiling) {
            final byte b = buffer.get(offset);
            // if the next byte is MSB0 then we've read all bytes
            if ((b & 0x80) == 0) {
                return result;
            }
            result = result | ((long) (b & 0x7F) << shift);
            shift = shift + 7;
            offset = offset + 1;
        }
        // we've hit the ceiling or amount of bits to read
        if (offset != ceiling || shift == 77) {
            throw new InvalidObjectException("Malformed variable length long");
        } else {
            return result;
        }
    }

    /**
     * Reads backwards 64-bit integer encoded as series of MSB1 bytes.
     *
     * @param buffer buffer where to read from
     * @param offset where to start reading
     * @param floor  where to stop reading, exclusive
     * @return 64-bit integer
     * @throws IOException
     */
    private long readVarLenLong1Back(BufferFacade buffer, long offset, long floor) throws IOException {
        int shift = 0;
        long result = 0;
        while (shift <= 70 && floor < offset) {
            final byte b = buffer.get(offset);
            if ((b & 0x80) == 0) {
                return result;
            }
            result = (result << 7) | (long) (b & 0x7F);
            shift = shift + 7;
            offset = offset - 1;
        }

        if (offset != floor || shift == 77) {
            throw new InvalidObjectException("Malformed variable length long");
        } else {
            return result;
        }
    }

    /**
     * Writes 64-bit integer encoded as a series of MSB0 bytes.
     *
     * @param out stream to write to
     * @throws IOException
     */
    private void writeVarLenLong0(long value, OutputStream out) throws IOException {
        while (true) {
            if ((value & ~0x7FL) == 0) {
                out.write((int) value & 0x7F);
                return;
            } else {
                out.write((int) value & 0x7F);
                value = value >>> 7;
            }
        }
    }

    /**
     * Reads 64-bit integer encoded as a series of MSB0 bytes.
     *
     * @param buffer  buffer where to read from
     * @param offset  where to start reading
     * @param ceiling where to stop reading, exclusive
     * @return 64-bit integer
     * @throws IOException
     */
    private long readVarLenLong0(BufferFacade buffer, long offset, long ceiling) throws IOException {
        int shift = 0;
        long result = 0;
        while (shift <= 70 && offset < ceiling) {
            final byte b = buffer.get(offset);
            // if the next byte is MSB1 then we've read all bytes
            if ((b & 0x80) == 0x80) {
                return result;
            }
            result = result | ((long) (b) << shift);
            shift = shift + 7;
            offset = offset + 1;
        }
        // we've hit the ceiling or amount of bits to read
        if (offset != ceiling || shift == 77) {
            throw new InvalidObjectException("Malformed variable length long");
        } else {
            return result;
        }
    }

    /**
     * Reads backwards 64-bit integer encoded as series of MSB0 bytes.
     *
     * @param buffer buffer where to read from
     * @param offset where to start reading
     * @param floor  where to stop reading, exclusive
     * @return 64-bit integer
     * @throws IOException
     */
    private long readVarLenLong0Back(BufferFacade buffer, long offset, long floor) throws IOException {
        int shift = 0;
        long result = 0;
        while (shift <= 70 && floor < offset) {
            final byte b = buffer.get(offset);
            // if the next byte is MSB1 then we've read all bytes
            if ((b & 0x80) == 0x80) {
                return result;
            }
            result = (result << 7) | (long) (b);
            shift = shift + 7;
            offset = offset - 1;
        }
        // we've hit the floor or amount of bits to read
        if (offset != (floor) || shift == 77) {
            throw new InvalidObjectException("Malformed variable length long");
        } else {
            return result;
        }
    }

    /**
     * Searches for char {@code c} in the buffer[low, high) of pairs [char, offset] encoded as [MSB1, MSB0]
     *
     * @param buffer where to search
     * @param c      char to search for
     * @param low    lower boundary, inclusive
     * @param high   higher boundary, exclusive
     * @return offset
     * @throws IOException
     */
    private long binarySearchChildren(BufferFacade buffer, char c, long low, long high) throws IOException {
        while (low < high) {
            long midRange = low + ((high - low) / 2);

            // During search, we jump approximately
            // to the middle of the search range and then
            // scan bytes backwards until we encounter the beginning
            // of a key, which will either be the byte at the
            // very start of the index range or a byte with the flag
            // bit set to ‘1’ immediately preceded by a byte with
            // the flag bit set to ‘0’. We then read the respective
            // key and compare it against the search key.
            while ((low < midRange)
                    && !((0 == (buffer.get(midRange - 1) & 0x80L)) && (0x80L == (buffer.get(midRange) & 0x80L)))) {
                midRange = midRange - 1;
            }

            long midVal = readVarLenLong1(buffer, midRange, high);
            char midChar = (char) midVal;
            long midOffset = readVarLenLong0(buffer, midRange + getVarLenLongSize(midVal), high);

            if (midChar < c)
                low = midRange + getVarLenLongSize(midVal) + getVarLenLongSize(midOffset);
            else if (midChar > c)
                high = midRange;
            else
                return midOffset; // key found
        }
        return -1;  // key not found.
    }

    /**
     * Returns value corresponding to key <code>k</code>.
     * @param k key
     * @return value corresponding to key <code>k</code>
     * @throws IOException
     */
    public Long get(String k) throws IOException {
        if (null == k) {
            throw new NullPointerException();
        }
        if ("".equals(k)) {
            throw new IllegalArgumentException();
        }

        Long result = null;

        int curLetter = 0;
        int idx = Arrays.binarySearch(rootChars, k.charAt(curLetter));
        if (0 <= idx) {
            long currentOffset = rootOffsets[idx];
            long nodeValue = rootValues[idx];
            boolean hasChildren = rootHasChildren[idx];
            boolean isWord = rootIsWord[idx];
            long offset = currentOffset + getVarLenLongSize(nodeValue << 2);

            curLetter = curLetter + 1;
            while (curLetter < k.length() && hasChildren) {
                long sizeOfIndex = readVarLenLong01(buffer, offset);
                offset = offset + getVarLenLongSize(sizeOfIndex);

                // binary search among children
                long relOffset = binarySearchChildren(buffer, k.charAt(curLetter), offset, offset + sizeOfIndex);

                if (-1 < relOffset) {
                    curLetter = curLetter + 1;
                    currentOffset = currentOffset - relOffset;
                    // read record at currentOffset
                    nodeValue = readVarLenLong01(buffer, currentOffset);
                    offset = currentOffset + getVarLenLongSize(nodeValue);
                    hasChildren = 0 < (nodeValue & 0x2L);
                    isWord = 0 < (nodeValue & 0x1L);
                    nodeValue = nodeValue >> 2;
                } else {
                    break;
                }
            }

            if (curLetter == k.length() && isWord) {
                result = nodeValue;
            }
        }
        return result;
    }

    /**
     * Iterates over all entries where key fits the <code>pattern</code>.
     * The _ (underscore) is a mask character in the pattern.
     *
     * @param pattern pattern
     * @return iterator
     */
    public PatternIterator iteratePatterns(String pattern) {
        return new PatternIterator(pattern, -1, -1);
    }

    /**
     * Iterates over all entries where key fits the <code>pattern</code>, respecting page boundaries.
     * The _ (underscore) is a mask character in the pattern.
     *
     * @param pattern pattern
     * @param pageNo page number, 0-based
     * @param pageSize page size
     * @return iterator
     */
    public PatternIterator iteratePatterns(String pattern, long pageNo, long pageSize) {
        return new PatternIterator(pattern, pageNo, pageSize);
    }

    /**
     * Iterates over all values where key fits the <code>pattern</code>.
     * The _ (underscore) is a mask character in the pattern.
     *
     * @param pattern pattern
     * @return iterator
     */
    public LongIterator iterateValues(String pattern) {
        return new LongIterator(pattern, -1, -1);
    }

    /**
     * Iterates over all values where key fits the <code>pattern</code>, respecting page boundaries.
     * The _ (underscore) is a mask character in the pattern.
     *
     * @param pattern pattern
     * @param pageNo page number, 0-based
     * @param pageSize page size
     * @return iterator
     */
    public LongIterator iterateValues(String pattern, long pageNo, long pageSize) {
        return new LongIterator(pattern, pageNo, pageSize);
    }

    private static class PackedTrieEntry implements Map.Entry<String, Long> {
        private final String k;
        private Long v;

        private PackedTrieEntry(String k, Long v) {
            this.k = k;
            this.v = v;
        }


        @Override
        public String getKey() {
            return k;
        }

        @Override
        public Long getValue() {
            return v;
        }

        @Override
        public Long setValue(Long value) {
            Long old = v;
            v = value;
            return old;
        }
    }

    public class PatternIterator extends TrieIterator implements Iterator<Map.Entry<String, Long>> {

        public PatternIterator(String pattern, long pageNo, long pageSize) {
            super(pattern, pageNo, pageSize);
        }

        @Override
        public boolean hasNext() {
            return super.hasNext();
        }

        @Override
        public Map.Entry<String, Long> next() {
            if (hasNext()) {
                PackedTrieEntry result = new PackedTrieEntry(k, v);
                k = null;
                return result;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            super.remove();
        }
    }

    public class LongIterator extends TrieIterator implements Iterator<Long> {

        public LongIterator(String pattern, long pageNo, long pageSize) {
            super(pattern, pageNo, pageSize);
        }

        @Override
        public boolean hasNext() {
            return super.hasNext();
        }

        @Override
        public Long next() {
            if (hasNext()) {
                Long result = v;
                k = null;
                return result;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            super.remove();
        }
    }

    private class TrieIterator {

        protected final String pattern;
        protected final long pageNo;
        protected final long pageSize;

        // node offsets and -1 as level marks
        protected Deque<Long> q = new ArrayDeque<Long>();
        // characters to process
        protected Deque<Character> c = new ArrayDeque<Character>();
        // current key
        protected StringBuilder s = new StringBuilder();
        // current letter in pattern
        protected int curLetter = 0;

        // next key
        protected String k = null;
        // next value
        protected Long v = null;

        // key count, for paging
        protected long count = 0;

        public TrieIterator(String pattern, long pageNo, long pageSize) {
            this.pattern = pattern;
            this.pageNo = pageNo;
            this.pageSize = pageSize;

            if (null == pattern) {
                throw new IllegalArgumentException("pattern should not be null");
            }

            if (0 < pattern.length()) {
                if ('_' == pattern.charAt(0)) {
                    for (int i = 0; i < rootOffsets.length; i++) {
                        q.addLast(rootOffsets[i]);
                        c.addLast(rootChars[i]);
                    }
                } else {
                    int idx = Arrays.binarySearch(rootChars, pattern.charAt(0));
                    if (-1 < idx) {
                        q.addLast(rootOffsets[idx]);
                        c.addLast(rootChars[idx]);
                    }
                }
            }

            if (0 < pageNo && 0 < pageSize) {
                long skip = pageNo * pageSize;
                while (hasNext() && 0 < skip) {
                    k = null;
                    skip--;
                }
            }
        }

        public boolean hasNext() {
            if (null == k && (pageSize <= 0 || (count < pageSize * (pageNo + 1)))) {
                try {
                    long nodeOffset;
                    long nodeValue;
                    boolean hasChildren;
                    boolean isWord;

                    long offset;
                    while (!q.isEmpty()) {
                        nodeOffset = q.removeFirst();

                        // go back one level
                        while (-1L == nodeOffset && !q.isEmpty()) {
                            s.deleteCharAt(s.length() - 1);
                            nodeOffset = q.removeFirst();
                            curLetter--;
                        }

                        if (-1L != nodeOffset) {
                            // root node has no character associated
                            if (!c.isEmpty()) {
                                s.append(c.removeFirst());
                            }

                            offset = nodeOffset;
                            nodeValue = readVarLenLong01(buffer, offset);
                            offset = offset + getVarLenLongSize(nodeValue);
                            hasChildren = 0 < (nodeValue & 0x2L);
                            isWord = 0 < (nodeValue & 0x1L);
                            nodeValue = nodeValue >> 2;

                            if (isWord && curLetter == (pattern.length() - 1)) {
                                // visit
                                k = s.toString();
                                v = nodeValue;
                                count++;
                            }

                            if (hasChildren && (curLetter + 1) < pattern.length()) {
                                long sizeOfIndex = readVarLenLong01(buffer, offset);
                                offset = offset + getVarLenLongSize(sizeOfIndex);

                                long low = offset;
                                long high = offset + sizeOfIndex;

                                // go one level down
                                curLetter++;
                                if ('_' == pattern.charAt(curLetter)) {
                                    // mark going level down
                                    q.addFirst(-1L);

                                    // add all children backwards, because q is stack
                                    offset = high - 1; // high is exclusive
                                    while (low < offset) {
                                        // read relOffset
                                        long relOffset = readVarLenLong0Back(buffer, offset, low - 1);
                                        offset = offset - getVarLenLongSize(relOffset);
                                        // read indexKey
                                        long indexKey = readVarLenLong1Back(buffer, offset, low - 1);
                                        offset = offset - getVarLenLongSize(indexKey);

                                        q.addFirst(nodeOffset - relOffset);
                                        c.addFirst((char) indexKey);
                                    }
                                } else {
                                    // find the letter
                                    long relOffset = binarySearchChildren(buffer, pattern.charAt(curLetter), low, high);
                                    if (-1 < relOffset) {
                                        // mark going level down
                                        q.addFirst(-1L);
                                        q.addFirst(nodeOffset - relOffset);
                                        c.addFirst(pattern.charAt(curLetter));
                                    } else {
                                        s.deleteCharAt(s.length() - 1);
                                        curLetter--;
                                    }
                                }
                            } else {
                                s.deleteCharAt(s.length() - 1);
                            }
                        } else {
                            curLetter--;
                        }

                        if (null != k) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null != k;
        }

        /**
         * Number of iterated keys.
         *
         * @return number of iterated keys
         */
        public long count() {
            return count;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public String getPattern() {
            return pattern;
        }

        public long getPageNo() {
            return pageNo;
        }

        public long getPageSize() {
            return pageSize;
        }
    }
}
