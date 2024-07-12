package az.travellab.ms_travel_application.util;

import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import org.hibernate.query.TupleTransformer;

import static java.lang.String.valueOf;

public class SalesSearchResponseTransformerUtil implements TupleTransformer<SalesSearchResponse> {
    public static final SalesSearchResponseTransformerUtil SALES_SEARCH_RESPONSE_TRANSFORMER_UTIL = new SalesSearchResponseTransformerUtil();

    @Override
    public SalesSearchResponse transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        return new SalesSearchResponse(
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i++]),
                valueOf(tuple[i])
        );
    }
}
