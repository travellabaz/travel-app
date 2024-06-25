package az.travellab.ms_travel_application.util;

import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import org.hibernate.query.TupleTransformer;

import static java.lang.String.valueOf;

public class SalesResponseTransformerUtil implements TupleTransformer<SalesSearchResponse> {
    public static final SalesResponseTransformerUtil SALES_RESPONSE_TRANSFORMER = new SalesResponseTransformerUtil();

    @Override
    public SalesSearchResponse transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        return new SalesSearchResponse( //todo fix response style
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
