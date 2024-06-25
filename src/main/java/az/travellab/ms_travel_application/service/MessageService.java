package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.annotation.Log;
import az.travellab.ms_travel_application.client.Soft10Client;
import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import az.travellab.ms_travel_application.logger.TravelLabLogger;
import az.travellab.ms_travel_application.model.enums.Employee;
import az.travellab.ms_travel_application.model.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static az.travellab.ms_travel_application.factory.MessageMapper.MESSAGE_MAPPER;
import static java.lang.Thread.sleep;
import static java.time.LocalDateTime.now;
import static org.springframework.data.domain.PageRequest.of;


@Log
@Service
@RequiredArgsConstructor
public class MessageService {

    private final Soft10Client soft10Client;
    private final OfferRepository offerRepository;
    private final TravelLabLogger log = TravelLabLogger.getLogger(MessageService.class);

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

        var fileUrl = messageRequest.getFileUrl();
        phones.addAll(messageRequest.getPhones());
        sendMessage(messageRequest.getPhoneFrom(), messageRequest.getMessage(), phones, fileUrl);
        offerRepository.saveAll(offerEntities);
    }

    public void sendMessage(String phoneFrom, String message, List<String> phones, String fileUrl) {
        phones.forEach(
                phone -> {
                    try {
                        soft10Client.sendMessage(
                                Employee.getEmployeeByPhone(phoneFrom).getAccessToken(),
                                MESSAGE_MAPPER.generateSendMessageRequest(
                                        phone,
                                        message,
                                        phoneFrom,
                                        fileUrl
                                )
                        );
                        sleep(12000);
                    } catch (Exception exception) {
                        log.error("ActionLog.decode.error: ", exception);
                    }
                }
        );
    }
}
