package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.response.EmployeesBonusResponse;
import az.travellab.ms_travel_application.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(path = "v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/bonuses")
    public List<EmployeesBonusResponse> getEmployeeBonuses(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String employeePhoneNumber
    ) {
        return reportService.getEmployeeBonus(fromDate, toDate, employeePhoneNumber);
    }
}
