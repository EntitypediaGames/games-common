package org.entitypedia.games.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

/**
 * Provides access to updatable properties, such as updatable-gameframework.properties
 * Initializes Spring Environment to add itself as a property source.
 * Itself it loads properties using commons PropertiesConfiguration from a file in classpath
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class UpdatableProperties implements InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(UpdatableProperties.class);

    private String propertiesPath;
    private final String propertiesName = getClass().getCanonicalName();

    private Properties backupProperties;
    private String backupPropertiesName;

    private Thread updatablePropertiesChangeListenerThread;

    @Autowired
    private ConfigurableEnvironment environment;

    public UpdatableProperties() {
    }

    public UpdatableProperties(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null != backupProperties) {
            environment.getPropertySources().addLast(new PropertiesPropertySource(backupPropertiesName, backupProperties));
        }

        ClassPathResource propertiesResource = new ClassPathResource(propertiesPath);
        try {
            UpdatablePropertiesChangeListener listener = new UpdatablePropertiesChangeListener(propertiesResource.getFile());
            updatablePropertiesChangeListenerThread = new Thread(listener, UpdatablePropertiesChangeListener.class.getSimpleName());
            updatablePropertiesChangeListenerThread.start();
        } catch (FileNotFoundException e) {
            log.warn(e.getMessage());
        }
    }


    @Override
    public void destroy() throws Exception {
        if (null != updatablePropertiesChangeListenerThread) {
            updatablePropertiesChangeListenerThread.interrupt();
        }
    }

    public String getPropertiesPath() {
        return propertiesPath;
    }

    public void setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    public Properties getBackupProperties() {
        return backupProperties;
    }

    public void setBackupProperties(Properties backupProperties) {
        this.backupProperties = backupProperties;
    }

    public String getBackupPropertiesName() {
        return backupPropertiesName;
    }

    public void setBackupPropertiesName(String backupPropertiesName) {
        this.backupPropertiesName = backupPropertiesName;
    }

    private class UpdatablePropertiesChangeListener implements Runnable {

        private final File configFile;

        private UpdatablePropertiesChangeListener(File configFile) {
            this.configFile = configFile;
        }

        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        public void run() {
            configurationChanged();

            try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
                Path path = Paths.get(configFile.getParent());
                path.register(watchService, java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    try {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.context() instanceof Path) {
                                Path changed = (Path) event.context();
                                if (changed.endsWith(configFile.getName())) {
                                    log.debug(configFile.getAbsolutePath() + " has changed. Reloading...");
                                    configurationChanged();
                                }
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        log.debug(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        public void configurationChanged() {
            log.info("Loading properties from " + configFile.getAbsolutePath());
            Properties p = new Properties();
            try (FileInputStream inputStream = new FileInputStream(configFile);
                 InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) {
                p.load(reader);
                PropertiesPropertySource pps = new PropertiesPropertySource(propertiesName, p);

                if (environment.getPropertySources().contains(propertiesName)) {
                    environment.getPropertySources().replace(propertiesName, pps);
                } else {
                    environment.getPropertySources().addFirst(pps);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}