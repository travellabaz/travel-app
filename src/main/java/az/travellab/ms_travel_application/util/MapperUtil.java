package az.travellab.ms_travel_application.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.InputStream;

import static az.travellab.ms_travel_application.factory.ObjectMapperFactory.MAPPER_FACTORY;


public enum MapperUtil {
    MAPPER_UTIL;

    public <T> String map(T source) {
        try {
            return MAPPER_FACTORY.createObjectMapperInstance().writeValueAsString(source);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    public <T> T map(InputStream source, Class<T> clazz) {
        try {
            return MAPPER_FACTORY.createObjectMapperInstance().readValue(source, clazz);
        } catch (Exception exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    public <T> T map(String source, Class<T> clazz) {
        try {
            return MAPPER_FACTORY.createObjectMapperInstance().readValue(source, clazz);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }
}