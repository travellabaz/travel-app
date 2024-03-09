package az.travellab.ms_travel_application.model.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MessageRequest {
    private List<String> phones;
    private String phoneFrom;
    private String message;
    private Long cityId;
    private LocalDateTime checkDate;
}
