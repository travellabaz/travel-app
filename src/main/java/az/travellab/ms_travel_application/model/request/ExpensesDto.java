package az.travellab.ms_travel_application.model.request;

import az.travellab.ms_travel_application.model.enums.ExpensesType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExpensesDto {
    private Long id;
    private BigDecimal amount;
    private ExpensesType type;
    private String note;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
