package az.travellab.ms_travel_application.model.enums;

import az.travellab.ms_travel_application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.nameFrom;
import static az.travellab.ms_travel_application.dao.entity.ClientEntity.Fields.phoneFrom;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.cancelReason;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.clientClass;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.employeeBonus;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.isEmployeeBonusPaid;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.isOfficial;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.note;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.number;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.profit;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.purchasedAmount;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.salesperson;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.soldAmount;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.status;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.tripEndDate;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.tripStartDate;
import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.type;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.FIELD_NOT_FOUND;
import static java.util.Arrays.stream;

@RequiredArgsConstructor
public enum SalesFieldsQueryExpressions {
    NUMBER(number, " number ILIKE :%s"),
    IS_OFFICIAL(isOfficial, " is_official ILIKE :%s"),
    TYPE(type, " type ILIKE :%s"),
    CLIENT_CLASS(clientClass, " class ILIKE :%s"),
    CLIENT_NAME(nameFrom, " client_name ILIKE :%s"),
    CLIENT_PHONE(phoneFrom, " client_phone ILIKE :%s"),
    CLIENT_PIN("clientPin", " client_pin ILIKE :%s"),
    CITY("city", " city_name ILIKE :%s"),
    COUNTRY("country", " country_name ILIKE :%s"),
    SALESPERSON(salesperson, " salesperson ILIKE :%s"),
    PURCHASED_AMOUNT(purchasedAmount, " purchased_amount IS :%s"),
    SOLD_AMOUNT(soldAmount, " sold_amount IS :%s"),
    TRIP_START_DATE(tripStartDate, " trip_start_date LIKE :%s"),
    TRIP_END_DATE(tripEndDate, " trip_end_date LIKE :%s"),
    EMPLOYEE_BONUS(employeeBonus, " employee_bonus LIKE :%s"),
    IS_EMPLOYEE_BONUS_PAID(isEmployeeBonusPaid, " is_employee_bonus_paid ILIKE :%s"),
    PROFIT(profit, " profit IS :%s"),
    STATUS(status, " status ILIKE :%s"),
    CANCEL_REASON(cancelReason, " cancel_reason ILIKE :%s"),
    NOTE(note, " note ILIKE :%s");

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