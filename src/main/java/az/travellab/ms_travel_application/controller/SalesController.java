package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import az.travellab.ms_travel_application.model.response.SalesChangeLogResponse;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import az.travellab.ms_travel_application.service.SalesCalculationService;
import az.travellab.ms_travel_application.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(path = "v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;
    private final SalesCalculationService salesCalculationService;

    @GetMapping("info")
    public SalesInfoResponse getInfo(@RequestParam String salesNumber) {
        return salesService.getInfo(salesNumber);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public String create(@RequestBody SalesRequest salesRequest) {
        return salesService.create(salesRequest);
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void update(
            @RequestHeader String user,
            @RequestBody SalesRequest salesRequest
    ) {
        salesService.update(user, salesRequest);
    }

    @GetMapping("changelogs")
    public List<SalesChangeLogResponse> getChangeLogs(@RequestParam String salesNumber) {
        return salesService.getChangeLogs(salesNumber);
    }

    //TODO adding update functionality of changelog

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("changelogs")
    public void deleteChangeLogs(
            @RequestParam String salesNumber,
            @RequestParam Integer version
    ) {
        salesService.deleteChangeLogs(salesNumber, version);
    }

    @PutMapping("confirm")
    @ResponseStatus(NO_CONTENT)
    public void confirm(
            @RequestParam String salesNumber,
            @RequestParam(required = false) Integer version) {
        salesService.confirm(salesNumber, version);
    }

    @GetMapping("/filter")
    public CommonPageableResponse<SalesInfoResponse> filter() {
        return salesService.filter();
    }

    @PutMapping("/calculate")
    @ResponseStatus(NO_CONTENT)
    public void calculate(@RequestParam String salesNumber) {
        salesCalculationService.calculate(salesNumber, Optional.empty());
    }
}
