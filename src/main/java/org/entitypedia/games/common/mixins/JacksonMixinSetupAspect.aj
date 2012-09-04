package org.entitypedia.games.common.mixins;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.entitypedia.games.common.util.HibernateAwareObjectMapper;
import org.entitypedia.games.common.util.MapperSavingJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.intercept.aspectj.MethodInvocationAdapter;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdvice;
import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Applies mixins from annotations to Jackson Mapper.
 *
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public aspect JacksonMixinSetupAspect implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(JacksonMixinSetupAspect.class);

    private PreInvocationAuthorizationAdvice preAdvice;

    private PrePostInvocationAttributeFactory attributeFactory;

    private MapperSavingJackson2HttpMessageConverter converter;

    private Constructor<MethodInvocationAdapter> constructor;

    @Override
    public void afterPropertiesSet() throws Exception {
        // hack package local constructor...
        constructor = MethodInvocationAdapter.class.getDeclaredConstructor(JoinPoint.class);
        constructor.setAccessible(true);

        if (null == preAdvice) {
            throw new IllegalArgumentException("org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice required!");
        }
        if (null == attributeFactory) {
            throw new IllegalArgumentException("org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory required!");
        }
        if (null == converter) {
            throw new IllegalArgumentException("org.entitypedia.games.common.util.MapperSavingJackson2HttpMessageConverter required!");
        }
    }

    pointcut serviceExecution(): execution(@JacksonMixins * org.entitypedia.games..service..*(..));
    pointcut controllerExecution(): execution(* org.entitypedia.games..api.controller..*(..));
    after() returning: serviceExecution() && cflowbelow(controllerExecution()) {
        // this sets the filtering mapper. this mapper is restored in converter.writeInternal.
        // between this and writeInternal there are a few Spring methods,
        // if there is an exception there, before writeInternal, then the mapper is stuck...

        // the mapper set by annotation is the only one
        // therefore one needs to check the control flow of the annotated method to ensure
        // there is no other mixins-annotated method being called inside the body
        // e.g. createGame calls readCrossword

        // jackson allows only one mixin per class... another incompatibility with complex call trees

        if (thisJoinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
            // gets interface method
            Method method = signature.getMethod();
            if (null != method) {
                JacksonMixins annotation = method.getAnnotation(JacksonMixins.class);
                if (null != annotation && null != annotation.value() && 0 < annotation.value().length) {
                    ObjectMapper mapper = new HibernateAwareObjectMapper();
                    List<String> mixinNames = new ArrayList<String>();
                    for (int i = 0; i < annotation.value().length; i++) {
                        JacksonMixin mixin = annotation.value()[i];
                        boolean apply = true;
                        if (!"".equals(mixin.condition())) {
                            // evaluate condition
                            PreInvocationAttribute attribute = attributeFactory.createPreInvocationAttribute(null, null, mixin.condition());
                            try {
                                apply = preAdvice.before(
                                        SecurityContextHolder.getContext().getAuthentication(),
                                        constructor.newInstance(thisJoinPoint),
                                        attribute);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (apply) {
                            mapper.addMixInAnnotations(mixin.target(), mixin.mixin());
                            mixinNames.add(mixin.target().getSimpleName() + "=" + mixin.mixin().getSimpleName());
                        }
                    }

                    if (0 < mixinNames.size() && mapper != converter.getObjectMapper()) {
                        converter.saveObjectMapper();
                        log.debug("Applying filters to serialization: {}", Arrays.toString(mixinNames.toArray()));
                        converter.setObjectMapper(mapper);
                    }
                }
            }
        }
    }

    public void setConverter(MapperSavingJackson2HttpMessageConverter converter) {
        this.converter = converter;
    }

    public void setAttributeFactory(PrePostInvocationAttributeFactory attributeFactory) {
        this.attributeFactory = attributeFactory;
    }

    public void setPreAdvice(PreInvocationAuthorizationAdvice preAdvice) {
        this.preAdvice = preAdvice;
    }
}
