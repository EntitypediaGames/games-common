package org.entitypedia.games.common.tries;

import static org.junit.Assert.*;

import org.entitypedia.games.common.buffer.BufferFacadeFactory;
import org.entitypedia.games.common.repository.util.UIDGenerator;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(JUnit4.class)
public class TestPackedTrie {

    @Test
    public void testPackEmptyTrie() throws IOException {
        BasicTrie t = new BasicTrie();
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        byte[] pack = out.toByteArray();
        assertEquals(2, pack.length);
        assertEquals(-128, pack[0]);
        assertEquals(-128, pack[1]);
    }

    @Test
    public void testPackSmallTrie() throws IOException {
        BasicTrie t = new BasicTrie();
        t.addWord("a");
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        byte[] pack = out.toByteArray();
        assertEquals(6, pack.length);
        assertEquals(-127, pack[0]);
        assertEquals(-126, pack[1]);
        assertEquals(-126, pack[2]);
        assertEquals(-31, pack[3]);
        assertEquals(1, pack[4]);
        assertEquals(-127, pack[5]);
    }

    @Test
    public void testPackSmallTrieId() throws IOException {
        BasicTrie t = new BasicTrie();
        BasicTrieNode a = t.addWord("a");
        a.setId(100);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        byte[] pack = out.toByteArray();
        assertEquals(7, pack.length);
        assertEquals(17, pack[0]);
        assertEquals(-125, pack[1]);
        assertEquals(-126, pack[2]);
        assertEquals(-126, pack[3]);
        assertEquals(-31, pack[4]);
        assertEquals(2, pack[5]);
        assertEquals(-126, pack[6]);
    }

    private BasicTrie createSample() {
        BasicTrie t = new BasicTrie();
        BasicTrieNode a = t.addWord("a");
        a.setId(100);
        BasicTrieNode abc = t.addWord("abc");
        abc.setId(200);
        BasicTrieNode abe = t.addWord("abé");
        abe.setId(300);
        BasicTrieNode bc = t.addWord("bc");
        bc.setId(400);
        return t;
    }

    @Test
    public void testPackTrieId() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        byte[] pack = out.toByteArray();
        assertEquals(29, pack.length);
        assertEquals("[33, -122, 49, -119, -126, -123, -29, 4, -23, -127, 2, 19, -125, -126, -30, 7, 65, -116, -126, -126, -29, 2, -126, -124, -31, 11, -30, 4, -106]", Arrays.toString(pack));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPackTrieHugeId() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        BasicTrie t = new BasicTrie();
        BasicTrieNode a = t.addWord("a");
        a.setId(Long.MAX_VALUE);
        p.pack(t, out);
    }

    @Test
    public void testLoadEmptyTrie() throws IOException {
        BasicTrie t = new BasicTrie();
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        assertNotNull(p);
        assertNull(p.get("a"));
        assertFalse(p.iteratePatterns("_").hasNext());
        assertFalse(p.iteratePatterns("_", 0, 10).hasNext());
        assertFalse(p.iterateValues("_").hasNext());
        assertFalse(p.iterateValues("_", 0, 10).hasNext());
    }

    @Test(expected = NullPointerException.class)
    public void testGetNPE() throws IOException {
        BasicTrie t = new BasicTrie();
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        p.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIAE() throws IOException {
        BasicTrie t = new BasicTrie();
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        p.get("");
    }

    @Test
    public void testGet() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        assertEquals(100, (long) p.get("a"));
        assertEquals(200, (long) p.get("abc"));
        assertEquals(300, (long) p.get("abé"));
        assertEquals(400, (long) p.get("bc"));
        assertNull(p.get("c"));
        assertNull(p.get("ab"));
        assertNull(p.get("b"));
        assertNull(p.get("abcd"));
    }

    @Test
    public void testEmptyPatternIterator() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        PackedTrie.PatternIterator pi = p.iteratePatterns("");
        assertFalse(pi.hasNext());
        pi = p.iteratePatterns("", -1, -1);
        assertFalse(pi.hasNext());
        pi = p.iteratePatterns("", 0, 10);
        assertFalse(pi.hasNext());
    }

    @Test
    public void testEmptyValueIterator() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        PackedTrie.LongIterator pi = p.iterateValues("");
        assertFalse(pi.hasNext());
        pi = p.iterateValues("", -1, -1);
        assertFalse(pi.hasNext());
        pi = p.iterateValues("", 0, 10);
        assertFalse(pi.hasNext());
    }

    @Test
    public void testPatternIteratorOne() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        PackedTrie.PatternIterator pi = p.iteratePatterns("_");
        assertTrue(pi.hasNext());
        Map.Entry<String, Long> e = pi.next();
        assertEquals("a", e.getKey());
        assertEquals(100, (long) e.getValue());
        assertFalse(pi.hasNext());

        pi = p.iteratePatterns("_", 0, 1);
        assertTrue(pi.hasNext());
        e = pi.next();
        assertEquals("a", e.getKey());
        assertEquals(100, (long) e.getValue());
        assertFalse(pi.hasNext());
        pi = p.iteratePatterns("_", 1, 1);
        assertFalse(pi.hasNext());
    }

    @Test
    public void testPatternIteratorTwo() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        PackedTrie.PatternIterator pi = p.iteratePatterns("__");
        assertTrue(pi.hasNext());
        Map.Entry<String, Long> e = pi.next();
        assertEquals("bc", e.getKey());
        assertEquals(400, (long) e.getValue());
        assertFalse(pi.hasNext());

        pi = p.iteratePatterns("__", 0, 1);
        assertTrue(pi.hasNext());
        e = pi.next();
        assertEquals("bc", e.getKey());
        assertEquals(400, (long) e.getValue());
        assertFalse(pi.hasNext());
        pi = p.iteratePatterns("_", 1, 1);
        assertFalse(pi.hasNext());
    }

    @Test
    public void testPatternIteratorThree() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        PackedTrie p = new PackedTrie();
        p.pack(createSample(), out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));
        PackedTrie.PatternIterator pi = p.iteratePatterns("___");
        assertTrue(pi.hasNext());
        Map.Entry<String, Long> e = pi.next();
        assertEquals("abc", e.getKey());
        assertEquals(200, (long) e.getValue());
        assertTrue(pi.hasNext());
        e = pi.next();
        assertEquals("abé", e.getKey());
        assertEquals(300, (long) e.getValue());
        assertFalse(pi.hasNext());

        pi = p.iteratePatterns("___", 0, 1);
        assertTrue(pi.hasNext());
        e = pi.next();
        assertEquals("abc", e.getKey());
        assertEquals(200, (long) e.getValue());
        assertFalse(pi.hasNext());

        pi = p.iteratePatterns("___", 1, 1);
        assertTrue(pi.hasNext());
        e = pi.next();
        assertEquals("abé", e.getKey());
        assertEquals(300, (long) e.getValue());
        assertFalse(pi.hasNext());
    }

    @Test
    public void testRandom() throws IOException {
        TreeMap<String, Long> source = new TreeMap<>();
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            source.put(UIDGenerator.getUID(r.nextInt(50) + 1), (long) r.nextInt());
        }

        BasicTrie t = new BasicTrie();
        for (Map.Entry<String, Long> e : source.entrySet()) {
            BasicTrieNode n = t.addWord(e.getKey());
            n.setId(e.getValue());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));

        for (Map.Entry<String, Long> e : source.entrySet()) {
            Long l = p.get(e.getKey());
            assertNotNull(l);
            assertEquals(l, e.getValue());

            l = p.get(e.getKey() + "_");
            assertNull(l);
        }
    }

    @Test
    public void testPagedIterator() throws IOException {
        final int cnt = 23;
        TreeMap<String, Long> source = new TreeMap<>();
        Random r = new Random();
        for (int i = 0; i < cnt; i++) {
            source.put(UIDGenerator.getUID(5), (long) r.nextInt());
        }

        BasicTrie t = new BasicTrie();
        for (Map.Entry<String, Long> e : source.entrySet()) {
            BasicTrieNode n = t.addWord(e.getKey());
            n.setId(e.getValue());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 1024);
        PackedTrie p = new PackedTrie();
        p.pack(t, out);
        p = new PackedTrie(BufferFacadeFactory.create(ByteBuffer.wrap(out.toByteArray())));

        long pageNo = 0;
        long pageSize = 10;
        long count = 0;
        PackedTrie.PatternIterator pi = p.iteratePatterns("_____", pageNo, pageSize);
        while (pi.hasNext()) {
            while (pi.hasNext()) {
                Map.Entry<String, Long> e = pi.next();
                count++;

                assertNotNull(e);
                assertNotNull(e.getKey());
                assertNotNull(e.getValue());

                Long l = source.get(e.getKey());
                assertNotNull(l);
                assertEquals(l, e.getValue());
            }

            assertTrue(0 == count % pageSize || cnt % pageSize == count % pageSize);

            pageNo++;
            pi = p.iteratePatterns("_____", pageNo, pageSize);
        }

        assertEquals(pageNo, (cnt / pageSize) + 1);
        assertEquals(count, cnt);
    }
}
