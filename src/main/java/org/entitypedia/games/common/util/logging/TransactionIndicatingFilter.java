package org.entitypedia.games.common.util.logging;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * From http://java.dzone.com/articles/monitoring-declarative-transac?page=0,1
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class TransactionIndicatingFilter extends Filter {
    @Override
    public int decide(LoggingEvent loggingEvent) {
        loggingEvent.setProperty("xaName", TransactionIndicatingUtil.getTransactionStatus(true));
        loggingEvent.setProperty("xaStatus", TransactionIndicatingUtil.getTransactionStatus(false));
        return Filter.NEUTRAL;
    }
}