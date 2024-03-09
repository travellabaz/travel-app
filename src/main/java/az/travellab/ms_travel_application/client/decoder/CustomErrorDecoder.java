package az.travellab.ms_travel_application.client.decoder;


import az.travellab.ms_travel_application.exception.CustomFeignException;
import az.travellab.ms_travel_application.logger.TravelLabLogger;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.experimental.FieldDefaults;

import static az.travellab.ms_travel_application.client.decoder.JsonNodeFieldName.MESSAGE;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.CLIENT_ERROR;
import static az.travellab.ms_travel_application.util.MapperUtil.MAPPER_UTIL;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomErrorDecoder implements ErrorDecoder {

    static TravelLabLogger log = TravelLabLogger.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        var errorMessage = CLIENT_ERROR.getMessage();

        JsonNode jsonNode;
        try (var body = response.body().asInputStream()) {
            jsonNode = MAPPER_UTIL.map(body, JsonNode.class);
        } catch (Exception e) {
            throw new CustomFeignException(CLIENT_ERROR.getMessage(), response.status());
        }

        if (jsonNode.has(MESSAGE.getValue())) errorMessage = jsonNode.get(MESSAGE.getValue()).asText();

        log.error("ActionLog.decode.error Message: {}, Method: {} ", errorMessage, methodKey);
        return new CustomFeignException(errorMessage, response.status());
    }
}