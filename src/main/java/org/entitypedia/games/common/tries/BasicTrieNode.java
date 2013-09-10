package org.entitypedia.games.common.tries;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple Trie node implementation for bootstrapping packed trie. Based on the code from Wikipedia.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class BasicTrieNode {

    private final char ch;
    // word id
    private long id;

    // offset in the file
    private long offset;
    private Map<Character, BasicTrieNode> children = Collections.emptyMap();
    private boolean isValidWord;

    public BasicTrieNode(char argChar) {
        ch = argChar;
    }

    public boolean addChild(BasicTrieNode argChild) {
        if (children.containsKey(argChild.getChar())) {
            return false;
        }

        if (0 == children.size()) {
            children = new TreeMap<Character, BasicTrieNode>();
        }
        children.put(argChild.getChar(), argChild);
        return true;
    }

    public char getChar() {
        return ch;
    }

    public BasicTrieNode getChild(char c) {
        return children.get(c);
    }

    public boolean isWord() {
        return isValidWord;
    }

    public void setIsWord(boolean argIsWord) {
        isValidWord = argIsWord;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public Collection<BasicTrieNode> getChildren() {
        return children.values();
    }
}