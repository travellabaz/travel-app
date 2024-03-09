package az.travellab.ms_travel_application.config;

import az.travellab.ms_travel_application.exception.CommonException;
import az.travellab.ms_travel_application.exception.ErrorHandler;
import az.travellab.ms_travel_application.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;

import static az.travellab.ms_travel_application.exception.ExceptionMessages.TOKEN_INVALID;
import static az.travellab.ms_travel_application.model.constants.HeaderConstants.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class TokenValidatorConfig extends OncePerRequestFilter {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ApplicationProperty applicationProperty;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader(AUTHORIZATION);
        if (token == null || !token.equals(applicationProperty.getToken())) {
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
