package org.entitypedia.games.common.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * Forwards all requests to underlying PropertiesConfiguration.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class PropertiesConfigurationPropertySource extends PropertySource<Map<String, String>> {

    private PropertiesConfiguration configuration;

    public PropertiesConfigurationPropertySource(String name) {
        super(name);
    }

    public PropertiesConfigurationPropertySource(String name, PropertiesConfiguration configuration) {
        super(name);
        this.configuration = configuration;
    }

    @Override
    public Object getProperty(String name) {
        return configuration.getProperty(name);
    }

    public PropertiesConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }
}