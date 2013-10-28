package org.entitypedia.games.common;

import org.entitypedia.games.common.repository.hibernateimpl.filter.TestFilterCriteriaParser;
import org.entitypedia.games.common.tries.TestBasicTrie;
import org.entitypedia.games.common.tries.TestBasicTrieNode;
import org.entitypedia.games.common.tries.TestPackedTrie;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestFilterCriteriaParser.class,
        TestBasicTrie.class,
        TestBasicTrieNode.class,
        TestPackedTrie.class
})
public class GamesCommonTestSuite {
}
