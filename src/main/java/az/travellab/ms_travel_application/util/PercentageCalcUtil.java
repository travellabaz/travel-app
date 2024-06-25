package az.travellab.ms_travel_application.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class PercentageCalcUtil {

    public BigDecimal calculatePercentage(BigDecimal amount, BigDecimal term) {
        return amount.multiply(term).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}
