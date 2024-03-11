package az.travellab.ms_travel_application.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ApplicationProperty {
    @Value("${token.key}")
    private String token;
}

