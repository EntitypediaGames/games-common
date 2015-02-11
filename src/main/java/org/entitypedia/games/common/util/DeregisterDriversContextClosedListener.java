package org.entitypedia.games.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class DeregisterDriversContextClosedListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(DeregisterDriversContextClosedListener.class);

    @Value("${hibernate.connection.driver_class}")
    private String driverClass;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (null == event.getApplicationContext().getParent()) {
            // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                if (driverClass.equals(driver.getClass().getCanonicalName())) {
                    try {
                        DriverManager.deregisterDriver(driver);
                        log.info(String.format("deregistering jdbc driver: %s", driver));
                    } catch (SQLException e) {
                        log.error(String.format("Error deregistering driver %s", driver), e);
                    }
                    break;
                }
            }
        }
    }
}