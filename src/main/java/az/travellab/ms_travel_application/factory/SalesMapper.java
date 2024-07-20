package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.SalesChangeLogEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.dto.CityDto;
import az.travellab.ms_travel_application.model.dto.ClientDto;
import az.travellab.ms_travel_application.model.dto.CountryDto;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static az.travellab.ms_travel_application.factory.SalesChangeLogMapper.SALES_CHANGE_LOG_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesComponentsMapper.SALES_COMPONENTS_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesPaymentsMapper.SALES_PAYMENTS_MAPPER;
import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeNameByPhone;
import static az.travellab.ms_travel_application.model.enums.SalesStatus.PENDING_FOR_APPROVE;
import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;
import static az.travellab.ms_travel_application.util.MapperUtil.MAPPER_UTIL;

public enum SalesMapper {
    SALES_MAPPER;

    public SalesEntity generateSalesEntity(
            String salesNumber,
            ClientEntity client,
            List<CityEntity> cities,
            SalesRequest salesRequest
    ) {
        var salesDate = salesRequest.getCreatedAt() == null ? LocalDateTime.now() : salesRequest.getCreatedAt();

        var salesEntity = SalesEntity.builder()
                .number(salesNumber)
                .isOfficial(salesRequest.getIsOfficial())
                .type(salesRequest.getType())
                .clientClass(salesRequest.getClientClass())
                .client(client)
                .salesperson(getEmployeeNameByPhone(salesRequest.getSalespersonNumber()))
                .hasClientRelationship(salesRequest.getHasClientRelationship() != null)
                .tripStartDate(DATE_UTIL.sync(salesRequest.getTripStartDate()))
                .tripEndDate(DATE_UTIL.sync(salesRequest.getTripEndDate()))
                .employeeBonus(BigDecimal.ZERO)
                .isEmployeeBonusPaid(false)
                .status(PENDING_FOR_APPROVE)
                .cancelReason(null)
                .cities(cities)
                .createdAt(DATE_UTIL.sync(salesDate))
                .build();

        var components = salesRequest.getComponents().stream().map(
                request -> SALES_COMPONENTS_MAPPER.generateSalesComponentsEntity(salesEntity, request)).toList();

        var payments = salesRequest.getPayments().stream().map(
                request -> SALES_PAYMENTS_MAPPER.generateSalesPaymentsEntity(salesEntity, request)).toList();

        var changeLogs = new ArrayList<SalesChangeLogEntity>();
        changeLogs.add(SALES_CHANGE_LOG_MAPPER.generateSalesChangeLogEntity(Employee.ADMIN.getName(), salesEntity, 1, salesRequest));

        return salesEntity.setComponents(components).setPayments(payments).setChangelogs(changeLogs);
    }

    public void updateSalesEntity(
            int versionId,
            ClientEntity client,
            List<CityEntity> cities,
            SalesEntity salesEntity,
            SalesRequest salesRequest
    ) {
        salesEntity.getComponents().clear();
        salesEntity.getPayments().clear();

        salesRequest.getComponents().forEach(request -> {
            var component = SALES_COMPONENTS_MAPPER.generateSalesComponentsEntity(salesEntity, request);
            salesEntity.getComponents().add(component);
        });

        salesRequest.getPayments().forEach(request -> {
            var payment = SALES_PAYMENTS_MAPPER.generateSalesPaymentsEntity(salesEntity, request);
            salesEntity.getPayments().add(payment);
        });

        salesEntity.getChangelogs().forEach(changeLog ->
                changeLog.setIsApproved(changeLog.getVersionId().equals(versionId)));

        salesEntity
                .setIsOfficial(salesRequest.getIsOfficial())
                .setType(salesRequest.getType())
                .setClientClass(salesRequest.getClientClass())
                .setClient(client)
                .setCities(cities)
                .setSalesperson(getEmployeeNameByPhone(salesRequest.getSalespersonNumber()))
                .setHasClientRelationship(salesRequest.getHasClientRelationship())
                .setTripStartDate(DATE_UTIL.sync(salesRequest.getTripStartDate()))
                .setTripEndDate(DATE_UTIL.sync(salesRequest.getTripEndDate()))
                .setEmployeeBonus(BigDecimal.ZERO)
                .setIsEmployeeBonusPaid(salesRequest.getIsEmployeeBonusPaid())
                .setCancelReason(salesRequest.getCancelReason())
                .setCreatedAt(DATE_UTIL.sync(salesRequest.getCreatedAt()));
    }

    private SalesInfoResponse populateResponseFromSalesEntity(SalesEntity salesEntity) {
        var response = new SalesInfoResponse();
        BeanUtils.copyProperties(salesEntity, response, "client", "components", "payments", "countries");

        var componentsDto = salesEntity.getComponents().stream()
                .map(SALES_COMPONENTS_MAPPER::generateComponentsDto)
                .toList();

        var paymentsDto = salesEntity.getPayments().stream()
                .map(SALES_PAYMENTS_MAPPER::generatePaymentsDto)
                .toList();

        response.setComponents(componentsDto);
        response.setPayments(paymentsDto);
        return response;
    }

    private SalesInfoResponse populateResponseFromSalesRequest(SalesRequest request) {
        var response = new SalesInfoResponse();
        BeanUtils.copyProperties(request, response, "clientDto");
        response.setComponents(request.getComponents());
        response.setPayments(request.getPayments());
        return response;
    }

    private ClientDto createClientDto(ClientEntity clientEntity) {
        return ClientDto.builder()
                .name(clientEntity.getNameFrom())
                .phone(clientEntity.getPhoneFrom())
                .pin(clientEntity.getPin())
                .build();
    }

    private List<CountryDto> createCountryDtos(List<CityEntity> cityEntities) {
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

        return new ArrayList<>(countryMap.values());
    }

    public SalesInfoResponse generateSalesInfoResponse(SalesEntity salesEntity, ClientEntity clientEntity, List<CityEntity> cityEntities) {
        var response = populateResponseFromSalesEntity(salesEntity);
        var client = createClientDto(clientEntity);
        var countries = createCountryDtos(cityEntities);

        return response
                .setClientDto(client)
                .setCountries(countries);
    }

    public SalesInfoResponse generateSalesInfoResponse(String requestString, ClientEntity clientEntity, List<CityEntity> cityEntities) {
        var request = MAPPER_UTIL.map(requestString, SalesRequest.class);
        var response = populateResponseFromSalesRequest(request);
        var client = createClientDto(clientEntity);
        var countries = createCountryDtos(cityEntities);

        return response
                .setClientDto(client)
                .setCountries(countries);
    }

    public SalesInfoResponse generateSalesInfoResponse(SalesSearchResponse filterResponse) {

        var response = new SalesInfoResponse();

        BeanUtils.copyProperties(filterResponse, response);

        var client = ClientDto.builder()
                .name(filterResponse.getClientName())
                .phone(filterResponse.getClientPhone())
                .pin(filterResponse.getClientPin())
                .build();

        return response
                .setClientDto(client);
    }
}
