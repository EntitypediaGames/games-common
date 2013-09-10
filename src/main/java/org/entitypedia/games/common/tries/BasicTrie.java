package org.entitypedia.games.common.tries;

/**
 * A simple Trie implementation to bootstrap packed trie. Based on the code from Wikipedia.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class BasicTrie {

    private BasicTrieNode root = new BasicTrieNode(' ');

    public BasicTrie() {
    }

    public BasicTrieNode addWord(String argWord) {
        return addWord(argWord.toCharArray());
    }

    public BasicTrieNode addWord(char[] argWord) {
        BasicTrieNode currentNode = root;

        for (char c : argWord) {
            if (currentNode.getChild(c) == null) {
                currentNode.addChild(new BasicTrieNode(c));
            }

            currentNode = currentNode.getChild(c);
        }

        currentNode.setIsWord(true);
        return currentNode;
    }

    public boolean containsPrefix(String argPrefix) {
        return contains(argPrefix.toCharArray(), false);
    }

    public boolean containsWord(String argWord) {
        return contains(argWord.toCharArray(), true);
    }

    public BasicTrieNode getWord(String argString) {
        BasicTrieNode node = getNode(argString.toCharArray());
        return node != null && node.isWord() ? node : null;
    }

    public BasicTrieNode getPrefix(String argString) {
        return getNode(argString.toCharArray());
    }

    public BasicTrieNode getRoot() {
        return root;
    }

    private boolean contains(char[] argString, boolean argIsWord) {
        BasicTrieNode node = getNode(argString);
        return (node != null && node.isWord() && argIsWord) || (!argIsWord && node != null);
    }

    private BasicTrieNode getNode(char[] argString) {
        BasicTrieNode currentNode = root;

        for (int i = 0; i < argString.length && currentNode != null; i++) {
            currentNode = currentNode.getChild(argString[i]);

            if (currentNode == null) {
                return null;
            }
        }

        return currentNode;
    }
}