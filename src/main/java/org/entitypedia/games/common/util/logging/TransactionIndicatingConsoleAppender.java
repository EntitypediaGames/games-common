package org.entitypedia.games.common.util.logging;

import org.apache.log4j.ConsoleAppender;

/**
 * From http://java.dzone.com/articles/monitoring-declarative-transac?page=0,1
 *
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class TransactionIndicatingConsoleAppender extends ConsoleAppender {
    public TransactionIndicatingConsoleAppender() {
        addFilter(new TransactionIndicatingFilter());
    }
}