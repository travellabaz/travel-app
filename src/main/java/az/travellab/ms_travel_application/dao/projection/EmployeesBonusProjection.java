package az.travellab.ms_travel_application.dao.projection;

import java.math.BigDecimal;

public interface EmployeesBonusProjection {
    String getSalesperson();

    BigDecimal getBonus();
}
