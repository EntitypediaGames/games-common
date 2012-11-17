package org.entitypedia.games.common.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * @author <a rel="author" href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class MapperSavingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(MapperSavingJackson2HttpMessageConverter.class);

    private final static ThreadLocal<ObjectMapper> tlObjectMapper = new ThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            return null;
        }
    };

    private boolean mapperInitialized = false;

    private boolean prefixJson = false;

    public MapperSavingJackson2HttpMessageConverter() {
        super();
    }

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        // no synchronization because Spring initializes it
        if (!mapperInitialized) {
            log.trace("{} sets super.objectMapper to {}", this, objectMapper);
            super.setObjectMapper(objectMapper);
            mapperInitialized = true;
        } else {
            log.trace("{} sets thread-local objectMapper to {}", this, objectMapper);
            tlObjectMapper.set(objectMapper);
        }
    }

    @Override
    public ObjectMapper getObjectMapper() {
        ObjectMapper result = tlObjectMapper.get();
        if (null == result) {
            result = super.getObjectMapper();
            log.trace("{} returns super.objectMapper {}", this, result);
        } else {
            log.trace("{} returns thread-local objectMapper {}", this, result);
        }
        return result;
    }

    @Override
    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
        super.setPrefixJson(prefixJson);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        JavaType javaType = getJavaType(clazz);
        return (getObjectMapper().canDeserialize(javaType) && canRead(mediaType));
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return (getObjectMapper().canSerialize(clazz) && canWrite(mediaType));
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        JavaType javaType = getJavaType(clazz);
        try {
            return getObjectMapper().readValue(inputMessage.getBody(), javaType);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator jsonGenerator =
                getObjectMapper().getFactory().createJsonGenerator(outputMessage.getBody(), encoding);
        try {
            if (this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            getObjectMapper().writeValue(jsonGenerator, object);
        } catch (IOException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected JavaType getJavaType(Class<?> clazz) {
        return getObjectMapper().constructType(clazz);
    }
}