package org.entitypedia.games.common.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author <a href="http://autayeu.com/">Aliaksandr Autayeu</a>
 */
public class MapperSavingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private static final Logger log = LoggerFactory.getLogger(MapperSavingJackson2HttpMessageConverter.class);

    private ThreadLocal<ObjectMapper> tlObjectMapper = new ThreadLocal<>();

    private boolean prefixJson = false;

    public MapperSavingJackson2HttpMessageConverter() {
        super();
    }

    public void setLocalObjectMapper(ObjectMapper objectMapper) {
        log.trace("{} sets thread-local objectMapper to {}", this, objectMapper);
        tlObjectMapper.set(objectMapper);
    }

    public void removeLocalObjectMapper() {
        tlObjectMapper.remove();
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
        return canRead(clazz, null, mediaType);
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        JavaType javaType = getJavaType(type, contextClass);
        return (getObjectMapper().canDeserialize(javaType) && canRead(mediaType));
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return (getObjectMapper().canSerialize(clazz) && canWrite(mediaType));
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        JavaType javaType = getJavaType(clazz, null);
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
                getObjectMapper().getFactory().createGenerator(outputMessage.getBody(), encoding);

        // A workaround for JsonGenerators not applying serialization features
        // https://github.com/FasterXML/jackson-databind/issues/12
        if (getObjectMapper().isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }

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
    protected JavaType getJavaType(Type type, Class<?> contextClass) {
        return (contextClass != null) ?
                getObjectMapper().getTypeFactory().constructType(type, contextClass) :
                getObjectMapper().constructType(type);
    }
}