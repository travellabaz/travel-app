package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.dto.CityDto;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.request.sales.SalesUpdateRequest;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static az.travellab.ms_travel_application.factory.SalesComponentsMapper.SALES_COMPONENTS_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesPaymentsMapper.SALES_PAYMENTS_MAPPER;
import static az.travellab.ms_travel_application.model.enums.SalesStatus.PENDING_FOR_APPROVE;

public enum SalesMapper {
    SALES_MAPPER;

    public SalesEntity generateSalesEntity(
            String salesNumber,
            String salesperson,
            ClientEntity client,
            List<CityEntity> cities,
            SalesRequest salesRequest) {

        var salesDate = salesRequest.getSalesDate() == null ? LocalDateTime.now() : salesRequest.getSalesDate();

        var salesEntity = SalesEntity.builder()
                .number(salesNumber)
                .isOfficial(salesRequest.getIsOfficial())
                .type(salesRequest.getType())
                .clientClass(salesRequest.getClientClass())
                .client(client)
                .cities(cities)
                .salesperson(salesperson)
                //.purchasedAmount(salesRequest.getPurchasedAmount())
                //.soldAmount(salesRequest.getSoldAmount())
                .tripStartDate(salesRequest.getTripStartDate())
                .tripEndDate(salesRequest.getTripEndDate())
                .employeeBonus(BigDecimal.ZERO)
                .isEmployeeBonusPaid(false)
                .status(PENDING_FOR_APPROVE)
                .cancelReason(null)
                .cities(cities)
                .createdAt(salesDate)
                .build();

        var components = salesRequest.getComponents().stream().map(
                request -> SALES_COMPONENTS_MAPPER.generateSalesComponentsEntity(salesEntity, request)).toList();

        var payments = salesRequest.getPayments().stream().map(
                request -> SALES_PAYMENTS_MAPPER.generateSalesPaymentsEntity(salesEntity, request)).toList();

        return salesEntity.setComponents(components).setPayments(payments);
    }

    public SalesInfoResponse generateSalesInfoResponse(SalesEntity salesEntity, ClientEntity clientEntity, List<CityEntity> cityEntities) {

        var response = new SalesInfoResponse();

        BeanUtils.copyProperties(salesEntity, response, "components", "payments", "countries");

        var componentsDto = salesEntity.getComponents().stream()
                .map(SALES_COMPONENTS_MAPPER::generateComponentsDto)
                .toList();

        var paymentsDto = salesEntity.getPayments().stream()
                .map(SALES_PAYMENTS_MAPPER::generatePaymentsDto)
                .toList();

        Map<String, CountryDto> countryMap = new HashMap<>();

        for (var city : cityEntities) {
            var cityDto = CityDto.builder()
                    .id(city.getId())
                    .name(city.getName())
                    .build();

            var countryName = city.getCountry().getName();
            var countryDto = countryMap.computeIfAbsent(countryName,
                    name -> CountryDto.builder()
                            .name(name)
                            .cities(new ArrayList<>())
                            .build());

            countryDto.getCities().add(cityDto);
        }

        var countries = new ArrayList<>(countryMap.values());

        return response.setClientName(clientEntity.getNameFrom())
                .setClientPhoneNumber(clientEntity.getPhoneFrom())
                .setClientPin(clientEntity.getPin())
                .setCountries(countries)
                .setComponents(componentsDto)
                .setPayments(paymentsDto);
    }

    public void updateSalesEntity(SalesEntity salesEntity, String salesperson, ClientEntity client,
                                  List<CityEntity> cities, SalesUpdateRequest request) {
        salesEntity
                .setIsOfficial(request.getIsOfficial())
                .setType(request.getType())
                .setClientClass(request.getClientClass())
                .setClient(client)
                .setCities(cities)
                .setSalesperson(salesperson)
                //.setPurchasedAmount(request.getPurchasedAmount())
                //.setSoldAmount(request.getSoldAmount())
                .setTripStartDate(request.getTripStartDate())
                .setTripEndDate(request.getTripEndDate())
                .setEmployeeBonus(request.getEmployeeBonus())
                .setIsEmployeeBonusPaid(request.getIsEmployeeBonusPaid())
                .setProfit(request.getProfit())
                .setStatus(request.getStatus())
                .setCancelReason(request.getCancelReason())
                .setCreatedAt(request.getSalesDate());
    }
}
