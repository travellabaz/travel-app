package az.travellab.ms_travel_application.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalesMessageQueries {
    GET_ALL_SALES("""
            WITH sales AS (
                SELECT
                    sales_table.id,
                    sales_table.number,
                    sales_table.type,
                    sales_table.class,
                    clients_table.name_from  AS client_name,
                    clients_table.phone_from AS client_phone,
                    clients_table.pin        AS client_pin,
                    sales_table.salesperson,
                    cities_table.name        AS city_name,
                    countries_table.name     AS country_name,
                    sales_table.purchased_amount,
                    sales_table.sold_amount,
                    sales_table.trip_start_date,
                    sales_table.trip_end_date,
                    sales_table.employee_bonus,
                    sales_table.profit,
                    sales_table.status,
                    sales_table.created_at,
                    ROW_NUMBER() OVER (
                        PARTITION BY sales_table.id
                        ORDER BY sales_table.created_at
                    ) AS rn
                FROM sales sales_table
                LEFT JOIN clients clients_table ON sales_table.client_id = clients_table.id
                LEFT JOIN sales_components sales_components_table ON sales_table.id = sales_components_table.sales_id
                LEFT JOIN sales_payments sales_payments_table ON sales_table.id = sales_payments_table.sales_id
                LEFT JOIN sales_city sales_city_table ON sales_table.id = sales_city_table.sales_id
                LEFT JOIN cities cities_table ON sales_city_table.city_id = cities_table.id
                LEFT JOIN countries countries_table ON cities_table.country_id = countries_table.id
            )
            SELECT
                id,
                number,
                type,
                class,
                client_name AS client_name,
                client_phone AS client_phone,
                UPPER(client_pin) AS client_pin,
                city_name,
                country_name,
                salesperson,
                purchased_amount,
                sold_amount,
                trip_start_date,
                trip_end_date,
                employee_bonus,
                profit,
                status,
                TO_CHAR(created_at + INTERVAL '4 hours', 'YYYY-MM-DD HH24:MI:SS') AS created_at
            FROM sales
            WHERE rn = 1
                """, " ORDER BY created_at DESC"),
    GET_ALL_SALES_COUNT("""
            SELECT
              COUNT(*)
            FROM
              (
                SELECT
                  sales_table.id,
                  sales_table.number,
                  sales_table.type,
                  sales_table.class,
                  clients_table.name_from AS client_name,
                  clients_table.phone_from AS client_phone,
                  clients_table.pin AS client_pin,
                  sales_table.salesperson,
                  cities_table.name AS city_name,
                  countries_table.name AS country_name,
                  sales_table.purchased_amount,
                  sales_table.sold_amount,
                  sales_table.trip_start_date,
                  sales_table.trip_end_date,
                  sales_table.employee_bonus,
                  sales_table.profit,
                  sales_table.status,
                  ROW_NUMBER() OVER (
                    PARTITION BY sales_table.id
                    ORDER BY sales_table.created_at
                  ) AS rn
                FROM
                  public.sales sales_table
                  LEFT JOIN public.clients clients_table ON sales_table.client_id = clients_table.id
                  LEFT JOIN public.sales_components sales_components_table ON sales_table.id = sales_components_table.sales_id
                  LEFT JOIN public.sales_payments sales_payments_table ON sales_table.id = sales_payments_table.sales_id
                  LEFT JOIN public.sales_city sales_city_table ON sales_table.id = sales_city_table.sales_id
                  LEFT JOIN public.cities cities_table ON sales_city_table.city_id = cities_table.id
                  LEFT JOIN public.countries countries_table ON cities_table.country_id = countries_table.id
              ) sales_subquery
            WHERE
              rn = 1
              """, " ORDER BY created_at DESC");

    private final String baseQuery;
    private final String endOfQuery;
}
