package az.travellab.ms_travel_application.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TelegramBotRequest {
    @JsonProperty("message_thread_in")
    private String messageThreadIn;
    @JsonProperty("chat_id")
    private String chatId;
    private String text;
}
