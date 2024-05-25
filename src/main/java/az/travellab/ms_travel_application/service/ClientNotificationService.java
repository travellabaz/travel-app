package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.travellab.ms_travel_application.model.enums.OfferStatus.BOUGHT;
import static az.travellab.ms_travel_application.model.enums.ServiceType.AIR_TICKET;
import static az.travellab.ms_travel_application.model.enums.ServiceType.TOUR;
import static az.travellab.ms_travel_application.model.enums.TripMessages.REMINDER_TRIP_ONE_DAY_BEFORE;
import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;
import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class ClientNotificationService {

    private final MessageService messageService;
    private final OfferRepository offerRepository;

    @Async
    public void sendTripReminderOneDayBefore() {
        var offers = offerRepository.findByTripDateBetweenAndStatusAndServiceTypeIn(
                DATE_UTIL.toStartOfNextDay(), DATE_UTIL.toEndOfNextDay(), BOUGHT, List.of(AIR_TICKET, TOUR)
        );

        if (offers.isEmpty()) return;

        offers.forEach(
                offer -> {
                    var client = offer.getClient();
                    messageService.sendMessage(
                            client.getPhoneTo(),
                            REMINDER_TRIP_ONE_DAY_BEFORE.getMessage().formatted(
                                    client.getNameFrom(),
                                    offer.getTripDate(),
                                    client.getNameTo(),
                                    client.getNameTo(),
                                    client.getPhoneTo()
                            ),
                            singletonList(client.getPhoneFrom()), null);
                }
        );
    }
}
