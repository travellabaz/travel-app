package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.CityEntity;
import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.dto.ClientDto;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.request.sales.SalesRequest;
import az.travellab.ms_travel_application.model.response.SalesInfoResponse;
import az.travellab.ms_travel_application.model.response.SalesSearchResponse;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static az.travellab.ms_travel_application.factory.SalesChangeLogMapper.SALES_CHANGE_LOG_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesComponentsMapper.SALES_COMPONENTS_MAPPER;
import static az.travellab.ms_travel_application.factory.SalesPaymentsMapper.SALES_PAYMENTS_MAPPER;
import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeNameByPhone;
import static az.travellab.ms_travel_application.model.enums.SalesStatus.PENDING_FOR_APPROVE;
import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;
import static az.travellab.ms_travel_application.util.SecureRandomUtil.generateUniqueNumber;

public enum SalesMapper {
    SALES_MAPPER;

    public SalesEntity generateSalesEntity(
            ClientEntity client,
            List<CityEntity> cities,
            SalesRequest salesRequest
    ) {
        var salesEntity = SalesEntity.builder()
                .number(generateUniqueNumber("TL/%08d-%d"))
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
                .cities(cities)
                .createdAt(DATE_UTIL.sync(salesRequest.getCreatedAt()))
                .updatedAt(DATE_UTIL.sync(DATE_UTIL.now()))
                .build();

        var components = salesRequest.getComponents().stream()
                .map(request -> SALES_COMPONENTS_MAPPER.generateSalesComponentsEntity(salesEntity, request))
                .collect(Collectors.toList());

        var payments = salesRequest.getPayments().stream()
                .map(request -> SALES_PAYMENTS_MAPPER.generateSalesPaymentsEntity(salesEntity, request))
                .collect(Collectors.toList());

        var changeLogs = List.of(SALES_CHANGE_LOG_MAPPER.generateSalesChangeLogEntity(
                Employee.ADMIN.getName(), salesEntity, 1, salesRequest));

        return salesEntity
                .setComponents(components)
                .setPayments(payments)
                .setChangelogs(changeLogs);
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

        salesEntity.getComponents().addAll(
                salesRequest.getComponents().stream()
                        .map(request -> SALES_COMPONENTS_MAPPER.generateSalesComponentsEntity(salesEntity, request))
                        .toList()
        );

        salesEntity.getPayments().addAll(
                salesRequest.getPayments().stream()
                        .map(request -> SALES_PAYMENTS_MAPPER.generateSalesPaymentsEntity(salesEntity, request))
                        .toList()
        );

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

    public SalesInfoResponse generateSalesInfoResponse(SalesSearchResponse filterResponse) {
        var response = new SalesInfoResponse();
        BeanUtils.copyProperties(filterResponse, response);

        var client = ClientDto.builder()
                .name(filterResponse.getClientName())
                .phone(filterResponse.getClientPhone())
                .pin(filterResponse.getClientPin())
                .build();

        return response.setClient(client);
    }

    public SalesInfoResponse generateSalesInfoResponse(SalesEntity salesEntity) {
        var clientEntity = salesEntity.getClient();
        var client = ClientDto.builder()
                .name(clientEntity.getNameFrom())
                .phone(clientEntity.getPhoneFrom())
                .pin(clientEntity.getPin())
                .build();

        var components = salesEntity.getComponents().stream()
                .map(SALES_COMPONENTS_MAPPER::generateComponentsDto)
                .collect(Collectors.toList());

        var payments = salesEntity.getPayments().stream()
                .map(SALES_PAYMENTS_MAPPER::generatePaymentsDto)
                .collect(Collectors.toList());

        var cities = salesEntity.getCities().stream()
                .map(CityEntity::getId)
                .collect(Collectors.toList());

        return SalesInfoResponse.builder()
                .number(salesEntity.getNumber())
                .isOfficial(salesEntity.getIsOfficial())
                .type(salesEntity.getType())
                .clientClass(salesEntity.getClientClass())
                .salesperson(salesEntity.getSalesperson())
                .hasClientRelationship(salesEntity.getHasClientRelationship())
                .purchasedAmount(salesEntity.getPurchasedAmount())
                .soldAmount(salesEntity.getSoldAmount())
                .tripStartDate(salesEntity.getTripStartDate())
                .tripEndDate(salesEntity.getTripEndDate())
                .isEmployeeBonusPaid(salesEntity.getIsEmployeeBonusPaid())
                .employeeBonus(salesEntity.getEmployeeBonus())
                .profit(salesEntity.getProfit())
                .status(salesEntity.getStatus())
                .cancelReason(salesEntity.getCancelReason())
                .note(salesEntity.getNote())
                .createdAt(salesEntity.getCreatedAt())
                .status(salesEntity.getStatus())
                .client(client)
                .cities(cities)
                .components(components)
                .payments(payments)
                .build();
    }
}
