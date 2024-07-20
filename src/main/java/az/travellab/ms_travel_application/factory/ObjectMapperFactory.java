package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.aspect.LogIgnoreIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

public enum ObjectMapperFactory {
    MAPPER_FACTORY;

    public ObjectMapper getLogIgnoreObjectMapper() {
        var objectMapper = new ObjectMapper();
        var introspector = new LogIgnoreIntrospector();
        objectMapper.setAnnotationIntrospector(introspector);
        objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    public ObjectMapper createObjectMapperInstance() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}