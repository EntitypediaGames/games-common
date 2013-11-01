package org.entitypedia.games.common.util.logging;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class HibernateStatisticsLogger implements InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(HibernateStatisticsLogger.class);

    private final static long statisticsIntervalSeconds = 600;
    private ScheduledExecutorService scheduler;
    private long lastRun;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // hibernate cache statistics logging
        scheduler = Executors.newSingleThreadScheduledExecutor();
        lastRun = System.currentTimeMillis();
        Runnable statLogger = new Runnable() {
            public void run() {
                if ((System.currentTimeMillis() - lastRun) >= statisticsIntervalSeconds * 1000) {
                    log.info("Hibernate statistics...");
                    sessionFactory.getStatistics().logSummary();
                    lastRun = System.currentTimeMillis();
                }
            }
        };
        scheduler.scheduleAtFixedRate(statLogger, statisticsIntervalSeconds, statisticsIntervalSeconds, TimeUnit.SECONDS);
    }

    public void destroy() throws Exception {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }
}