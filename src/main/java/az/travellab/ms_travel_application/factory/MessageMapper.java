package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.model.client.SendMessageRequest;

import java.util.List;

import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeByPhone;

public enum MessageMapper {
    MESSAGE_MAPPER;

    public SendMessageRequest generateSendMessageRequest(List<String> sendPhones,String message,String phoneFrom) {
        var employee = getEmployeeByPhone(phoneFrom);
        return SendMessageRequest.builder()
//                .accessToken(employee.getAccessToken())
                .message(message)
                .numbers(sendPhones)
                .userId(employee.getUserId())
                .build();
    }
}
