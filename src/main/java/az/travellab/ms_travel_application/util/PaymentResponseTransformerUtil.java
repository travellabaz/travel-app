package az.travellab.ms_travel_application.util;

import az.travellab.ms_travel_application.model.response.ClientResponse;
import org.hibernate.query.TupleTransformer;

import static java.lang.String.valueOf;

public class PaymentResponseTransformerUtil implements TupleTransformer<ClientResponse> {
    public static final PaymentResponseTransformerUtil PAYMENT_RESPONSE_TRANSFORMER = new PaymentResponseTransformerUtil();
    @Override
    public ClientResponse transformTuple(Object[] tuple, String[] aliases) {
        int i= 0;
        return new ClientResponse(
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
