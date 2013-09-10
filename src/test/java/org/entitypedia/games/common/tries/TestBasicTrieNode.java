package org.entitypedia.games.common.tries;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JUnit4.class)
public class TestBasicTrieNode {

    @Test
    public void testConstructor() {
        BasicTrieNode node = new BasicTrieNode('a');
        assertEquals('a', node.getChar());
        assertEquals(0, node.getChildren().size());
        assertNull(node.getChild('a'));
    }

    @Test
    public void testSets() {
        BasicTrieNode node = new BasicTrieNode('a');
        node.setId(1L);
        node.setIsWord(true);
        node.setOffset(1L);
        assertEquals(1L, node.getId());
        assertEquals(true, node.isWord());
        assertEquals(1L, node.getOffset());
    }

    @Test
    public void testAddChild() {
        BasicTrieNode node = new BasicTrieNode('a');
        BasicTrieNode child = new BasicTrieNode('b');
        node.addChild(child);
        assertEquals(1, node.getChildren().size());
        assertEquals(child, node.getChildren().iterator().next());
        assertEquals(child, node.getChild('b'));
        assertEquals(null, node.getChild('a'));
        assertEquals(false, node.addChild(new BasicTrieNode('b')));
    }
}
