package az.travellab.ms_travel_application.model.client;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SendMessageRequest {
    private String userId;
    private String message;
    private List<String> numbers;
    private String fileUrl;
}
