package az.travellab.ms_travel_application.util;

import az.travellab.ms_travel_application.model.enums.ClientClass;
import az.travellab.ms_travel_application.model.enums.SalesStatus;
import az.travellab.ms_travel_application.model.enums.ServiceType;
import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import org.hibernate.query.TupleTransformer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesSearchResponseTransformerUtil implements TupleTransformer<SalesSearchResponse> {
    public static final SalesSearchResponseTransformerUtil SALES_SEARCH_RESPONSE_TRANSFORMER_UTIL = new SalesSearchResponseTransformerUtil();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    @Override
    public SalesSearchResponse transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        return new SalesSearchResponse(
                valueOfOrNull(tuple[i++]),
                Boolean.valueOf(valueOfOrNull(tuple[i++])),
                ServiceType.valueOf(valueOfOrNull(tuple[i++])),
                ClientClass.valueOf(valueOfOrNull(tuple[i++])),
                valueOfOrNull(tuple[i++]),
                valueOfOrNull(tuple[i++]),
                valueOfOrNull(tuple[i++]),
                valueOfOrNull(tuple[i++]),
                Boolean.valueOf(valueOfOrNull(tuple[i++])),
                toBigDecimalOrNull(tuple[i++]),
                toBigDecimalOrNull(tuple[i++]),
                parseLocalDateTimeOrNull(tuple[i++]),
                parseLocalDateTimeOrNull(tuple[i++]),
                toBigDecimalOrNull(tuple[i++]),
                Boolean.valueOf(valueOfOrNull(tuple[i++])),
                toBigDecimalOrNull(tuple[i++]),
                SalesStatus.valueOf(valueOfOrNull(tuple[i++])),
                valueOfOrNull(tuple[i++]),
                valueOfOrNull(tuple[i++]),
                parseLocalDateTimeOrNull(tuple[i])
        );
    }

    private String valueOfOrNull(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    private BigDecimal toBigDecimalOrNull(Object obj) {
        return obj != null ? new BigDecimal(obj.toString()) : null;
    }

    private LocalDateTime parseLocalDateTimeOrNull(Object obj) {
        return obj != null ? LocalDateTime.parse(obj.toString(), formatter) : null;
    }
}
