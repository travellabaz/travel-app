package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesComponentsEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import az.travellab.ms_travel_application.model.request.sales.SalesComponentsRequest;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public enum SalesComponentsMapper {
    SALES_COMPONENTS_MAPPER;

    public SalesComponentsEntity generateSalesComponentsEntity(SalesEntity salesEntity, SalesComponentsRequest componentsRequest) {

        var date = componentsRequest.getDate() == null ? LocalDateTime.now() : componentsRequest.getDate();

        return SalesComponentsEntity.builder()
                .id(componentsRequest.getId())
                .sales(salesEntity)
                .type(componentsRequest.getType())
                .name(componentsRequest.getName())
                .bookingNumber(componentsRequest.getBookingNumber())
                .percentage(componentsRequest.getPercentage())
                .description(componentsRequest.getDescription())
                .purchasedAmount(componentsRequest.getPurchasedAmount())
                .soldAmount(componentsRequest.getSoldAmount())
                .paidAmount(componentsRequest.getPaidAmount())
                .remainedAmount(componentsRequest.getRemainedAmount())
                .transferCommission(componentsRequest.getTransferCommission())
                .createdAt(date)
                .status(componentsRequest.getStatus())
                .build();
    }

    public void updateSalesComponentsEntity(SalesComponentsEntity componentsEntity, SalesComponentsRequest componentsRequest) {
        BeanUtils.copyProperties(componentsRequest, componentsEntity);
        componentsEntity.setCreatedAt(componentsRequest.getDate());
    }

    public SalesComponentsDto generateComponentsDto(SalesComponentsEntity componentsEntity) {
        var componentDto = new SalesComponentsDto();
        BeanUtils.copyProperties(componentsEntity, componentDto);
        return componentDto;
    }
}
