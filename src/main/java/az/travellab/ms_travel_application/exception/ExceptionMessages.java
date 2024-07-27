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
    TOKEN_INVALID("Token is invalid"),
    SALES_NOT_FOUND("Sales with id: %s not found"),
    CHANGELOG_NOT_FOUND("Changelog with id: %s not found"),
    SALES_ALREADY_EXISTS("Sales with id: %s already exists"),
    CLIENT_NOT_FOUND("Client with id: %s not found"),
    ENTITY_NOT_FOUND("Entity with id: %s not found");

    private final String message;
}