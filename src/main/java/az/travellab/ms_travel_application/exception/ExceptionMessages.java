package az.travellab.ms_travel_application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessages {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    NOT_FOUND_OFFER("Not found offer with id: %s"),
    CLIENT_ERROR("Exception from Client"),
    FIELD_NOT_FOUND("Field not found"),
    TOKEN_INVALID("Token is invalid");

    private final String message;
}