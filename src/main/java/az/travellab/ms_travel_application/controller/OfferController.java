package az.travellab.ms_travel_application.controller;

import az.travellab.ms_travel_application.annotation.Api;
import az.travellab.ms_travel_application.model.request.OfferRequest;
import az.travellab.ms_travel_application.model.response.OfferResponse;
import az.travellab.ms_travel_application.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Api(path = "v1/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/info")
    public List<OfferResponse> getOfferInfo(@RequestParam Long clientId, @RequestParam String phone) {
        return offerService.getOfferInfo(clientId, phone);
    }

    @PutMapping("/info/update")
    @ResponseStatus(NO_CONTENT)
    public void offerInfoUpdate(@RequestBody OfferRequest offerRequest) {
        offerService.offerInfoUpdate(offerRequest);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void createOffer(@RequestBody OfferRequest offerRequest) {
        offerService.createOffer(offerRequest);
    }
}
