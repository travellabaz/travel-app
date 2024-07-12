package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.repository.SalesRepository;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.response.EmployeesBonusResponse;
import az.travellab.ms_travel_application.util.PercentageCalcUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {

    private final SalesRepository salesRepository;

    public List<EmployeesBonusResponse> getEmployeeBonus(String fromDate, String toDate, String employeePhoneNumber) { //todo change this method calculation algorithm
        var employeeBonusProjection = salesRepository.getEmployeesBonus(fromDate, toDate);
        var response = new ArrayList<EmployeesBonusResponse>();

        var teamLeadBonus = BigDecimal.ZERO;
        for (var projection : employeeBonusProjection) {
            if (!projection.getSalesperson().equals(Employee.EMPLOYEE_1.getName())) {
                teamLeadBonus = teamLeadBonus.add(projection.getBonus());
            }

            response.add(
                    EmployeesBonusResponse.builder()
                            .salesperson(projection.getSalesperson())
                            .bonus(projection.getBonus())
                            .build()
            );
        }

        teamLeadBonus = PercentageCalcUtil.calculatePercentage(teamLeadBonus, BigDecimal.valueOf(20));

        var finalTeamLeadBonus = teamLeadBonus;
        response.stream()
                .filter(resp -> resp.getSalesperson().equals(Employee.EMPLOYEE_1.getName()))
                .findFirst()
                .ifPresent(resp -> resp.setBonus(resp.getBonus().add(finalTeamLeadBonus)));

        return response;
    }
}
