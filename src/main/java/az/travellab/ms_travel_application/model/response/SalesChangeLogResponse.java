package az.travellab.ms_travel_application.model.response;

import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SalesChangeLogResponse {
    private String username;
    private int version;
    private Boolean isApproved;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private SalesRequest request;
}
