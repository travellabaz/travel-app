package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.request.MessageRequest;
import az.travellab.ms_travel_application.model.response.CityResponse;
import az.travellab.ms_travel_application.service.CityService;
import az.travellab.ms_travel_application.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(path = "v1/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    @ResponseStatus(NO_CONTENT)
    public void sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.sendMessage(messageRequest);
    }
}
