package org.entitypedia.games.common.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;

/**
 * Provides access to updatable properties, such as updatable-gameframework.properties
 * Initializes Spring Environment to add itself as a property source.
 * Itself it loads properties using commons PropertiesConfiguration from a file in classpath
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class UpdatableProperties implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(UpdatableProperties.class);

    private String propertiesPath;

    @Autowired
    ConfigurableEnvironment environment;

    public UpdatableProperties() {
    }

    public UpdatableProperties(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource properties = new ClassPathResource(propertiesPath);
        try {
            String absolutePath = properties.getFile().getAbsolutePath();

            PropertiesConfiguration configuration = new PropertiesConfiguration(absolutePath);
            FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
            // minimum a minute of delay before going to check changed file date
            fileChangedReloadingStrategy.setRefreshDelay(60000);
            configuration.setReloadingStrategy(fileChangedReloadingStrategy);

            PropertiesConfigurationPropertySource configurationPropertySource =
                    new PropertiesConfigurationPropertySource(getClass().getSimpleName(), configuration);

            environment.getPropertySources().addFirst(configurationPropertySource);
        } catch (FileNotFoundException e) {
            log.error("UpdatableProperties can't find the file: " + e.getMessage());
        }
    }

    public String getPropertiesPath() {
        return propertiesPath;
    }

    public void setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }
}