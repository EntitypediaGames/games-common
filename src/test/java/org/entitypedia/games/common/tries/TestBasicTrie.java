package org.entitypedia.games.common.tries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestBasicTrie {

    @Test
    public void testConstructor() {
        BasicTrie t = new BasicTrie();
        assertNotNull(t.getRoot());
    }

    @Test
    public void testAddWord() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        assertEquals(1, t.getRoot().getChildren().size());
        BasicTrieNode a = t.getRoot().getChild('a');
        assertEquals('a', a.getChar());
        assertEquals(false, a.isWord());
        assertEquals(1, a.getChildren().size());
        BasicTrieNode b = a.getChild('b');
        assertEquals(true, b.getChildren().isEmpty());
        assertEquals(true, b.isWord());
    }

    @Test
    public void testContainsPrefix() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        assertEquals(true, t.containsPrefix("a"));
        assertEquals(true, t.containsPrefix("ab"));
        assertEquals(false, t.containsPrefix("abc"));
        assertEquals(false, t.containsPrefix("b"));
    }

    @Test
    public void testContainsWord() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        assertEquals(false, t.containsWord("a"));
        assertEquals(false, t.containsWord("abc"));
        assertEquals(false, t.containsWord("b"));
        assertEquals(true, t.containsWord("ab"));
    }

    @Test
    public void testGetWord() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        assertEquals(null, t.getWord("a"));
        BasicTrieNode ab = t.getWord("ab");
        assertNotNull(ab);
        assertEquals(true, ab.isWord());
        assertEquals(true, ab.getChildren().isEmpty());
    }

    @Test
    public void testGetPrefix() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        assertNotNull(t.getPrefix("a"));
        BasicTrieNode ab = t.getPrefix("ab");
        assertNotNull(ab);
        assertEquals(true, ab.isWord());
        assertEquals(true, ab.getChildren().isEmpty());
    }

    @Test
    public void testContainsPrefixLong() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        t.addWord("abc");
        t.addWord("bc");
        assertEquals(true, t.containsPrefix("a"));
        assertEquals(true, t.containsPrefix("ab"));
        assertEquals(true, t.containsPrefix("bc"));
        assertEquals(true, t.containsPrefix("abc"));
        assertEquals(true, t.containsPrefix("b"));
        assertEquals(false, t.containsPrefix(" "));
        assertEquals(false, t.containsPrefix("c"));
    }

    @Test
    public void testContainsWordLong() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        t.addWord("abc");
        t.addWord("bc");
        assertEquals(false, t.containsWord("a"));
        assertEquals(false, t.containsWord("abcd"));
        assertEquals(false, t.containsWord("b"));
        assertEquals(false, t.containsWord("bcd"));
        assertEquals(true, t.containsWord("ab"));
        assertEquals(true, t.containsWord("abc"));
        assertEquals(true, t.containsWord("bc"));
    }

    @Test
    public void testGetWordLong() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        t.addWord("abc");
        t.addWord("bc");
        assertEquals(null, t.getWord("a"));
        BasicTrieNode ab = t.getWord("ab");
        assertNotNull(ab);
        assertEquals(true, ab.isWord());
        assertEquals(false, ab.getChildren().isEmpty());
        BasicTrieNode abc = t.getWord("abc");
        assertNotNull(abc);
        assertEquals(true, abc.isWord());
        assertEquals(true, abc.getChildren().isEmpty());
        BasicTrieNode bc = t.getWord("bc");
        assertNotNull(bc);
        assertEquals(true, bc.isWord());
        assertEquals(true, bc.getChildren().isEmpty());
        BasicTrieNode b = t.getWord("b");
        assertNull(b);
    }

    @Test
    public void testGetPrefixLong() {
        BasicTrie t = new BasicTrie();
        t.addWord("ab");
        t.addWord("abc");
        t.addWord("bc");
        assertNotNull(t.getPrefix("a"));
        assertNotNull(t.getPrefix("b"));
        BasicTrieNode a = t.getPrefix("a");
        assertNotNull(a);
        assertEquals(false, a.isWord());
        assertEquals(false, a.getChildren().isEmpty());
    }

}
