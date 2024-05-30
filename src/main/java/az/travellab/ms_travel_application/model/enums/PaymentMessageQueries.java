package az.travellab.ms_travel_application.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum PaymentMessageQueries {
    GET_ALL_CLIENTS("""
            WITH clients_offers AS (SELECT cl.id,
                                                       cl.name_to,
                                                       cl.phone_to,
                                                       cl.name_from,
                                                       cl.phone_from,
                                                       cl.message,
                                                       cl.pin,
                                                       cl.mail,
                                                       cl.is_married,
                                                       cl.is_parent,
                                                       cl.username,
                                                       cl.gender_type,
                                                       cl.citizen_country,
                                                       cl.birth_date,
                                                       cl.created_at,
                                                       cl.message_sent_at,
                                                       o.service_type,
                                                       o.status,
                                                       toc.city_id,
                                                       t.country_id,
                                                       ROW_NUMBER() OVER (PARTITION BY cl.id ORDER BY cl.created_at) AS rn
                                                FROM clients cl
                                                         LEFT JOIN public.offers o on cl.id = o.client_id
                                                         LEFT JOIN public.travel_offer_city toc on o.id = toc.offer_id
                                                         LEFT JOIN public.travel_offer_country t on o.id = t.offer_id)
                        SELECT id,
                               name_to,
                               phone_to,
                               name_from,
                               phone_from,
                               message,
                               pin,
                               mail,
                               is_married,
                               is_parent,
                               username,
                               gender_type,
                               citizen_country,
                               birth_date,
                               to_char(created_at, 'YYYY-MM-DD HH24:MI:SS'),
                               message_sent_at,
                               service_type,
                               status,
                               city_id,
                               country_id
                        FROM clients_offers
                        WHERE rn = 1
            """," ORDER BY created_at DESC"),
    GET_ALL_CLIENTS_COUNT("""
            WITH clients_offers AS (SELECT cl.id,
                                                       cl.name_to,
                                                       cl.phone_to,
                                                       cl.name_from,
                                                       cl.phone_from,
                                                       cl.message,
                                                       cl.pin,
                                                       cl.mail,
                                                       cl.is_married,
                                                       cl.is_parent,
                                                       cl.username,
                                                       cl.gender_type,
                                                       cl.citizen_country,
                                                       cl.birth_date,
                                                       cl.created_at,
                                                       cl.message_sent_at,
                                                       o.service_type,
                                                       o.status,
                                                       toc.city_id,
                                                       t.country_id,
                                                       ROW_NUMBER() OVER (PARTITION BY cl.id ORDER BY cl.created_at) AS rn
                                                FROM clients cl
                                                         LEFT JOIN public.offers o on cl.id = o.client_id
                                                         LEFT JOIN public.travel_offer_city toc on o.id = toc.offer_id
                                                         LEFT JOIN public.travel_offer_country t on o.id = t.offer_id)
                        SELECT count(*)
                        FROM clients_offers
                        WHERE rn = 1
            """," ORDER BY created_at DESC");

    String baseQuery;
    String endOfQuery;
}
