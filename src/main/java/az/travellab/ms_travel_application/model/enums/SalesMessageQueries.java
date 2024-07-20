package az.travellab.ms_travel_application.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalesMessageQueries {

    GET_ALL_SALES("""
            WITH sales AS (SELECT sales_table.number,
                                  sales_table.is_official,
                                  sales_table.type,
                                  sales_table.class                   AS client_class,
                                  clients_table.name_from             AS client_name,
                                  clients_table.phone_from            AS client_phone,
                                  clients_table.pin                   AS client_pin,
                                  sales_table.salesperson,
                                  sales_table.has_client_relationship AS has_client_relationship,
                                  sales_table.purchased_amount,
                                  sales_table.sold_amount,
                                  sales_table.trip_start_date,
                                  sales_table.trip_end_date,
                                  sales_table.employee_bonus,
                                  sales_table.is_employee_bonus_paid,
                                  sales_table.profit,
                                  sales_table.status,
                                  sales_table.cancel_reason,
                                  sales_table.note,
                                  sales_table.created_at,
                                  ROW_NUMBER() OVER (
                                      PARTITION BY sales_table.id
                                      ORDER BY sales_table.created_at
                                      )                               AS rn
                           FROM sales sales_table
                                    LEFT JOIN clients clients_table ON sales_table.client_id = clients_table.id)
            SELECT number,
                   is_official,
                   type,
                   client_class,
                   client_name,
                   client_phone,
                   client_pin,
                   salesperson,
                   has_client_relationship,
                   purchased_amount,
                   sold_amount,
                   trip_start_date,
                   trip_end_date,
                   employee_bonus,
                   is_employee_bonus_paid,
                   profit,
                   status,
                   cancel_reason,
                   note,
                   created_at
            FROM sales
            WHERE rn = 1
            """);

    private final String baseQuery;
}
