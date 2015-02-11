package org.entitypedia.games.common.mixins;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for methods which need to filter JSON responses. Example:
 * <pre>
 * &#64;JacksonMixins({
 *   &#64;JacksonMixin(target = Crossword.class, mixin = CrosswordMixin.class),
 *   &#64;JacksonMixin(target = CrosswordUser.class, mixin = CrosswordUserMixin.class)
 *   })
 * </pre>
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JacksonMixins {
    JacksonMixin[] value();
}
