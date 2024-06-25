package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import az.travellab.ms_travel_application.model.dto.SalesPaymentsDto;
import az.travellab.ms_travel_application.model.request.sales.SalesComponentsRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesPaymentsRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesUpdateRequest;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import az.travellab.ms_travel_application.service.SalesCalculationService;
import az.travellab.ms_travel_application.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    @ResponseStatus(CREATED)
    public void createSales(@RequestBody SalesRequest salesRequest) {
        salesService.createSales(salesRequest);
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void updateSales(
            @RequestParam String salesNumber,
            @RequestBody SalesUpdateRequest request
    ) {
        salesService.updateSales(salesNumber, request);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void deleteSales(@RequestParam String salesNumber) {
        salesService.deleteSales(salesNumber);
    }

    @GetMapping("/components")
    public List<SalesComponentsDto> getSalesComponents(@RequestParam String salesNumber) {
        return salesService.getSalesComponents(salesNumber);
    }

    @GetMapping("/payments")
    public List<SalesPaymentsDto> getSalesPayments(@RequestParam String salesNumber) {
        return salesService.getSalesPayments(salesNumber);
    }

    @PostMapping("/components")
    @ResponseStatus(NO_CONTENT)
    public void updateSalesComponents(
            @RequestParam String salesNumber,
            @RequestBody List<SalesComponentsRequest> componentsRequest
    ) {
        salesService.updateComponents(salesNumber, componentsRequest);
    }

    @PostMapping("/payments")
    @ResponseStatus(NO_CONTENT)
    public void updateSalesPayments(
            @RequestParam String salesNumber,
            @RequestBody List<SalesPaymentsRequest> paymentsRequest
    ) {
        salesService.updatePayments(salesNumber, paymentsRequest);
    }

    @GetMapping("/info")
    public SalesInfoResponse getSalesInfo(@RequestParam String salesNumber) {
        return salesService.getSalesInfo(salesNumber);
    }

    @GetMapping("/all")
    public CommonPageableResponse<SalesSearchResponse> getAllSales() {
        return salesService.getSales();
    }

    @PutMapping("/calculate")
    @ResponseStatus(NO_CONTENT)
    public void calculate(@RequestParam String salesNumber) {
        salesCalculationService.calculate(salesNumber, Optional.empty());
    }
}
