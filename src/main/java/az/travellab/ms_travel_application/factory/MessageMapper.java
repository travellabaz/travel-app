package az.travellab.ms_travel_application.factory;


import az.travellab.ms_travel_application.model.client.SendMessageRequest;

import static az.travellab.ms_travel_application.model.enums.Employee.getEmployeeByPhone;
import static java.util.Collections.singletonList;

public enum MessageMapper {
    MESSAGE_MAPPER;

    public SendMessageRequest generateSendMessageRequest(String phone, String message, String phoneFrom, String fileUrl) {
        var employee = getEmployeeByPhone(phoneFrom);
        return SendMessageRequest.builder()
                .message(message)
                .numbers(singletonList(phone))
                .userId(employee.getUserId())
                .fileUrl(fileUrl)
                .build();
    }
}
