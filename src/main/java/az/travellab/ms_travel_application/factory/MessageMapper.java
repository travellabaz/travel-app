package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.model.client.SendMessageRequest;

import java.util.Collections;
import java.util.List;

import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeByPhone;
import static java.util.Collections.*;

public enum MessageMapper {
    MESSAGE_MAPPER;

    public SendMessageRequest generateSendMessageRequest(String phone,String message,String phoneFrom) {
        var employee = getEmployeeByPhone(phoneFrom);
        return SendMessageRequest.builder()
                .message(message)
                .numbers(singletonList(phone))
                .userId(employee.getUserId())
                .build();
    }
}
