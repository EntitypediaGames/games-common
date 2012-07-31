package org.entitypedia.games.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;

/**
 * See http://blog.pastelstudios.com/2012/03/12/spring-3-1-hibernate-4-jackson-module-hibernate/
 * Spring 3.2 should have this fixed.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        Hibernate3Module hm = new Hibernate3Module();
        hm.configure(Hibernate3Module.Feature.FORCE_LAZY_LOADING, true);
        registerModule(hm);
    }
}