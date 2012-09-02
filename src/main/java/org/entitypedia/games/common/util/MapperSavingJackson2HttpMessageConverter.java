package org.entitypedia.games.common.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.entitypedia.games.common.exceptions.MapperAlreadyOverwrittenException;
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

    private final ThreadLocal<ObjectMapper> tlObjectMapper = new ThreadLocal<ObjectMapper>();
    private final ThreadLocal<ObjectMapper> tlOldObjectMapper = new ThreadLocal<ObjectMapper>();

    private boolean prefixJson = false;

    public MapperSavingJackson2HttpMessageConverter() {
        super();
        tlOldObjectMapper.set(null);
    }

    public void saveObjectMapper() {
        if (null == tlOldObjectMapper.get()) {
            tlOldObjectMapper.set(getObjectMapper());
        } else {
            throw new MapperAlreadyOverwrittenException();
        }
    }

    public void restoreObjectMapper() {
        ObjectMapper objectMapper = tlOldObjectMapper.get();
        if (null != objectMapper) {
            log.debug("Restoring objectMapper {}", objectMapper);
            setObjectMapper(objectMapper);
            tlOldObjectMapper.set(null);
        }
    }

    @Override
    public void setObjectMapper(ObjectMapper objectMapper) {
        super.setObjectMapper(objectMapper);
        tlObjectMapper.set(objectMapper);
    }

    @Override
    public ObjectMapper getObjectMapper() {
        ObjectMapper result = tlObjectMapper.get();
        if (null == result) {
            result = super.getObjectMapper();
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
                getObjectMapper().getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
        try {
            if (this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            getObjectMapper().writeValue(jsonGenerator, object);
        } catch (IOException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        } finally {
            restoreObjectMapper();
        }
    }

    @Override
    protected JavaType getJavaType(Class<?> clazz) {
        return getObjectMapper().constructType(clazz);
    }
}