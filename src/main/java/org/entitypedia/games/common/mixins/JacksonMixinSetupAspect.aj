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

    private ThreadLocal<List<JacksonMixin>> tlMixins = new ThreadLocal<>();

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

    pointcut controllerExecution(): execution(@JacksonMixins * org.entitypedia.games..api.controller..*(..));
    after() returning: controllerExecution() {
        // this prepares mixins for the filtering mapper.

        // one needs to check the control flow of the annotated method to ensure
        // there is no other mixins-annotated method being called inside the body
        // e.g. createGame calls readCrossword, both annotated
        // and the latter overwrites the mixins of the former
        // in this case warning is printed

        // jackson allows only one mixin per class... can't accumulate mixins over complex call trees

        // mixins clear in writeInternal. therefore they might get stuck if method does not render JSON
        // e.g. updateFocus calls readGame for access control and mixins get stuck.
        // in this case warning is printed too, but it has little meaning

        if (thisJoinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
            // gets interface method
            Method method = signature.getMethod();
            if (null != method) {
                JacksonMixins annotation = method.getAnnotation(JacksonMixins.class);
                if (null != annotation && null != annotation.value() && 0 < annotation.value().length) {
                    List<JacksonMixin> mixins = tlMixins.get();
                    if (null != mixins && 0 < mixins.size()) {
                        if (log.isWarnEnabled()) {
                            log.warn("{} is overwriting mixins {}", thisJoinPoint, Arrays.toString(mixinsToStringArray(mixins)));
                        }
                        mixins.clear();
                    }

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
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (apply) {
                            if (null == mixins) {
                                mixins = new ArrayList<>(annotation.value().length);
                            }
                            mixins.add(mixin);
                        }
                    }

                    if (null != mixins && 0 < mixins.size()) {
                        tlMixins.set(mixins);
                        if (log.isDebugEnabled()) {
                            log.debug("{} prepared mixins {}", thisJoinPoint, Arrays.toString(mixinsToStringArray(mixins)));
                        }
                    } else {
                        log.debug("{} cleared mixins", thisJoinPoint);
                    }
                }
            }
        }
    }

    private String[] mixinsToStringArray(List<JacksonMixin> mixins) {
        String[] result = new String[mixins.size()];
        for (int i = 0; i < mixins.size(); i++) {
            result[i] = mixins.get(i).target().getSimpleName() + "=" + mixins.get(i).mixin().getSimpleName();
        }
        return result;
    }

    pointcut writeInternalExecution(): execution(* org.entitypedia.games.common.util.MapperSavingJackson2HttpMessageConverter.writeInternal(..));
    Object around(): writeInternalExecution() {
        List<JacksonMixin> mixins = tlMixins.get();
        if (null != mixins && 0 < mixins.size()) {
            // set up mapper
            ObjectMapper mapper = new HibernateAwareObjectMapper();
            for (JacksonMixin mixin : mixins) {
                mapper.addMixInAnnotations(mixin.target(), mixin.mixin());
            }

            log.debug("Applying mixins {} to {}", Arrays.toString(mixinsToStringArray(mixins)), converter);
            converter.setLocalObjectMapper(mapper);
        }

        try {
            Object result = proceed();
            return result;
        } finally {
            if (null != mixins && 0 < mixins.size()) {
                // restore mapper
                log.debug("Removing mixins from {}", converter);
                converter.removeLocalObjectMapper();
            }
            tlMixins.remove();
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
