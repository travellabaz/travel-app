package az.travellab.ms_travel_application.util;

import org.hibernate.query.TupleTransformer;

import static java.lang.String.valueOf;

public class SalesInfoResponseTransformerUtil implements TupleTransformer<String> {
    public static final SalesInfoResponseTransformerUtil SALES_INFO_RESPONSE_TRANSFORMER_UTIL = new SalesInfoResponseTransformerUtil();

    @Override
    public String transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        return valueOf(tuple[i]);
    }
}
