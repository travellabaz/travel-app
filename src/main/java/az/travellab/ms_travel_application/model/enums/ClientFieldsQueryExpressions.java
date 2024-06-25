package az.travellab.ms_travel_application.model.enums;

import az.travellab.ms_travel_application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.citizenCountry;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.genderType;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.isMarried;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.isParent;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.mail;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.message;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.nameFrom;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.nameTo;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.phoneFrom;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.phoneTo;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.pin;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.username;
import static az.travellab.ms_travel_application.dao.entity.OfferEntity.Fields.serviceType;
import static az.travellab.ms_travel_application.dao.entity.OfferEntity.Fields.status;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.number;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.FIELD_NOT_FOUND;
import static java.util.Arrays.stream;

@RequiredArgsConstructor
public enum ClientFieldsQueryExpressions {
    NUMBER(number, " upper(number) LIKE :%s"),
    NAME_TO(nameTo, " upper(name_to) like :%s"),
    PHONE_TO(phoneTo, " phone_to LIKE :%s"),
    NAME_FROM(nameFrom, " upper(name_from) like :%s"),
    PHONE_FROM(phoneFrom, " phone_from LIKE :%s"),
    MESSAGE(message, " upper(message) LIKE :%s"),
    PIN(pin, " upper(pin)= upper(:%s)"),
    MAIL(mail, " upper(mail) LIKE :%s"),
    IS_MARRIED(isMarried, " is_married= :%s"),
    IS_PARENT(isParent, " is_parent= :%s"),
    USERNAME(username, " upper(username)= upper(:%s)"),
    GENDER_TYPE(genderType, " upper(gender_type)= upper(:%s)"),
    CITIZEN_COUNTRY(citizenCountry, " upper(citizen_country)= upper(:%s)"),
    BIRTH_DATE_FROM("fromBirthDate", " date(birth_date) >= to_date(:%s, 'DD-MM-YYYY')"),
    BIRTH_DATE_TO("toBirthDate", " date(birth_date) <= to_date(:%s, 'DD-MM-YYYY')"),
    FROM_DATE("fromCreatedAt", " date(created_at) >= to_date(:%s, 'DD-MM-YYYY')"),
    TO_DATE("toCreatedAt", " date(created_at) <= to_date(:%s, 'DD-MM-YYYY')"),
    MESSAGE_SENT_DATE_FROM("fromMessageSentAt", " date(message_sent_at) >= to_date(:%s, 'DD-MM-YYYY')"),
    MESSAGE_SENT_DATE_TO("toMessageSentAt", " date(message_sent_at) <= to_date(:%s, 'DD-MM-YYYY')"),
    SERVICE_TYPE(serviceType, " service_type= :%s"),
    OFFER_STATUS(status, " status= :%s"),
    CITY_ID("cityId", " city_id= :%s"),
    COUNTRY_ID("countryId", " country_id= :%s");

    private final String fieldName;
    private final String expression;

    public static String getExpression(String paramName) {
        var fieldsQueryExpressions = stream(values())
                .filter(fieldsQueryExpression -> fieldsQueryExpression.fieldName.equals(paramName))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(FIELD_NOT_FOUND.getMessage()));
        return " and " + fieldsQueryExpressions.expression.formatted(fieldsQueryExpressions.fieldName);
    }
}