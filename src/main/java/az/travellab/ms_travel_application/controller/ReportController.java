package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.request.ExpensesDto;
import az.travellab.ms_travel_application.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(path = "v1")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/expenses")
    public List<ExpensesDto> getExpenses() {
        return reportService.getExpenses();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public void addExpenses(@RequestBody ExpensesDto expensesDto) {
        reportService.addExpenses(expensesDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void getExpenses(@RequestParam Long id) {
        reportService.deleteExpenses(id);
    }
}
