package org.entitypedia.games.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * See http://blog.pastelstudios.com/2012/03/12/spring-3-1-hibernate-4-jackson-module-hibernate/
 * Spring 3.2 should have this fixed.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
    // TODO AA Check if removal is possible
    public HibernateAwareObjectMapper() {
        Hibernate4Module hm = new Hibernate4Module();
        hm.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, true);
        registerModule(hm);
    }
}