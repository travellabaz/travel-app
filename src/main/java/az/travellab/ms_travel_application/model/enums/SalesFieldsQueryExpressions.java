package az.travellab.ms_travel_application.model.enums;

import az.travellab.ms_travel_application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

import static az.travellab.ms_travel_application.dao.entity.SalesEntity.Fields.*;
import static az.travellab.ms_travel_application.exception.ExceptionMessages.FIELD_NOT_FOUND;
import static java.util.Arrays.stream;

@RequiredArgsConstructor
public enum SalesFieldsQueryExpressions {
    NUMBER(number, " number ILIKE :%s"),
    IS_OFFICIAL(isOfficial, " is_official= :%s"),
    TYPE(type, " type ILIKE :%s"),
    CLIENT_CLASS(clientClass, " class ILIKE :%s"),
    CLIENT_NAME("clientName", " client_name ILIKE :%s"),
    CLIENT_PHONE("clientPhone", " client_phone ILIKE :%s"),
    CLIENT_PIN("clientPin", " client_pin ILIKE :%s"),
    CITY("city", " city_name ILIKE :%s"),
    COUNTRY("country", " country_name ILIKE :%s"),
    SALESPERSON(salesperson, " salesperson ILIKE :%s"),
    HAS_CLIENT_RELATIONSHIP(hasClientRelationship, " has_client_relationship= :%s"),
    PURCHASED_AMOUNT(purchasedAmount, " purchased_amount IS :%s"),
    SOLD_AMOUNT(soldAmount, " sold_amount IS :%s"),
    TRIP_START_DATE(tripStartDate, " DATE(trip_start_date) ILIKE :%s"),
    TRIP_END_DATE(tripEndDate, " DATE(trip_end_date) ILIKE :%s"),
    EMPLOYEE_BONUS(employeeBonus, " employee_bonus IS :%s"),
    IS_EMPLOYEE_BONUS_PAID(isEmployeeBonusPaid, " is_employee_bonus_paid= :%s"),
    PROFIT(profit, " profit IS :%s"),
    STATUS(status, " status ILIKE :%s"),
    CANCEL_REASON(cancelReason, " cancel_reason ILIKE :%s"),
    NOTE(note, " note ILIKE :%s"),
    DIAPASON_FROM_DATE("fromDate", " TO_CHAR(created_at, 'yyyy-mm-dd') BETWEEN :%s"),
    DIAPASON_TO_DATE("toDate", " :%s");

    private final String fieldName;
    private final String expression;

    public static String getExpression(String paramName) {
            var fieldsQueryExpressions = stream(values())
                    .filter(fieldsQueryExpression -> fieldsQueryExpression.fieldName.equals(paramName))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException(FIELD_NOT_FOUND.getMessage()));
            return " AND " + fieldsQueryExpressions.expression.formatted(fieldsQueryExpressions.fieldName);
    }
}