package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.client.Soft10Client;
import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static az.travellab.ms_travel_application.factory.MessageMapper.MESSAGE_MAPPER;
import static java.time.LocalDateTime.now;
import static org.springframework.data.domain.PageRequest.of;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final Soft10Client soft10Client;
    private final OfferRepository offerRepository;

    @Async
    public void sendMessage(MessageRequest messageRequest) {
        var offerEntities = offerRepository.findDistinctByCityEntityListIdAndMessageSentAtBefore(
                messageRequest.getCityId(),
                messageRequest.getCheckDate(),
                of(0, 100)
        );
        var messageSentAt = now();

        var phones = new ArrayList<>(offerEntities.stream()
                .map(offerEntity -> {
                    offerEntity.setMessageSentAt(messageSentAt);
                    return offerEntity.getClient().getPhoneFrom();
                }).toList());

        phones.addAll(messageRequest.getPhones());
        soft10Client.sendMessage(
                Employee.getEmployeeByPhone(messageRequest.getPhoneFrom()).getAccessToken(),
                MESSAGE_MAPPER.generateSendMessageRequest(
                        phones,
                        messageRequest.getMessage(),
                        messageRequest.getPhoneFrom()
                )
        );

        offerRepository.saveAll(offerEntities);
    }
}
