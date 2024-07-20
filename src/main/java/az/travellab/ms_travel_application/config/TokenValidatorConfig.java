package az.travellab.ms_travel_application.config;

import az.travellab.ms_travel_application.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static az.travellab.ms_travel_application.exception.ExceptionMessages.TOKEN_INVALID;
import static az.travellab.ms_travel_application.model.constants.HeaderConstants.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class TokenValidatorConfig extends OncePerRequestFilter {
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<String> tokens = List.of(
            "e50dafb992e030660a9dd87207fb4e99b8",
            "a82849063d96719c97c785b86f5f6789",
            "e259acc19ede38f3c8a16b5a26941afd",
            "19e1a633cfd23590e424f2de02dbd41f",
            "SRrdxDWe8NGM-/0uQrB!RHLtT2cbczHeT633kq2E3mXn2XrJSx69Y1JNjITNiVt1"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader(AUTHORIZATION);
        if (token == null || !tokens.contains(token)) {
            response.setStatus(BAD_REQUEST.value());
            response.setContentType(APPLICATION_JSON_VALUE);

            mapper.writeValue(
                    response.getWriter(),
                    new ErrorResponse(TOKEN_INVALID.getMessage())
            );
        }
        filterChain.doFilter(request, response);
    }
}
