package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.dao.entity.SalesComponentsEntity;
import az.travellab.ms_travel_application.dao.entity.SalesEntity;
import az.travellab.ms_travel_application.model.dto.SalesComponentsDto;
import org.springframework.beans.BeanUtils;

import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;

public enum SalesComponentsMapper {
    SALES_COMPONENTS_MAPPER;

    public SalesComponentsEntity generateSalesComponentsEntity(SalesEntity salesEntity, SalesComponentsDto componentsRequest) {
        return SalesComponentsEntity.builder()
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
                .createdAt(DATE_UTIL.sync(componentsRequest.getCreatedAt()))
                .updatedAt(DATE_UTIL.sync(DATE_UTIL.now()))
                .status(componentsRequest.getStatus())
                .build();
    }

    public SalesComponentsDto generateComponentsDto(SalesComponentsEntity componentsEntity) {
        var componentDto = new SalesComponentsDto();
        BeanUtils.copyProperties(componentsEntity, componentDto);
        return componentDto;
    }
}
