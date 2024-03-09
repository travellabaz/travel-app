package az.travellab.ms_travel_application.client.decoder;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum JsonNodeFieldName {
    MESSAGE("message");

    String value;
}
