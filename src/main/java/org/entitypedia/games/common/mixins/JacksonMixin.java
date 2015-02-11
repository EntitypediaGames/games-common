package org.entitypedia.games.common.mixins;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Pairs mixin and its target class.
 *
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JacksonMixin {

    /**
     * Target class to apply mixin to.
     *
     * @return class to apply mixin to
     */
    Class<?> target();

    /**
     * Mixin to apply to the target class.
     *
     * @return mixin to apply to the target class
     */
    Class<?> mixin();

    /**
     * Mixin application condition. Defaults to applying.
     *
     * @return application condition.
     */
    String condition() default "";
}
