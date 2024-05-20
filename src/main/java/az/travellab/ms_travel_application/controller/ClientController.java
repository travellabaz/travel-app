package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.request.ClientRegistrationRequest;
import az.travellab.ms_travel_application.model.request.ClientUpdateRequest;
import az.travellab.ms_travel_application.model.response.ClientResponse;
import az.travellab.ms_travel_application.model.response.CommonPageableResponse;
import az.travellab.ms_travel_application.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(path = "v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/registration")
    @ResponseStatus(CREATED)
    public void clientRegistration(@RequestBody ClientRegistrationRequest clientRegistrationRequest) {
        clientService.clientRegistration(clientRegistrationRequest);
    }

    @PutMapping("/info/update")
    @ResponseStatus(NO_CONTENT)
    public void clientInfoUpdate(@RequestBody ClientUpdateRequest clientUpdateRequest) {
        clientService.clientInfoUpdate(clientUpdateRequest);
    }

    @GetMapping("/info/phone/{phone}")
    public ClientResponse getClientInfo(@PathVariable String phone) {
        return clientService.getClientInfo(phone);
    }

    @GetMapping("/all")
    public CommonPageableResponse<ClientResponse> getClients() {
        return clientService.getClients();
    }
}
