package az.travellab.ms_travel_application.service;

import az.travellab.ms_travel_application.dao.entity.ClientEntity;
import az.travellab.ms_travel_application.dao.repository.ClientRepository;
import az.travellab.ms_travel_application.dao.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.travellab.ms_travel_application.model.enums.OfferStatus.BOUGHT;
import static az.travellab.ms_travel_application.model.enums.ServiceType.AIR_TICKET;
import static az.travellab.ms_travel_application.model.enums.ServiceType.TOUR;
import static az.travellab.ms_travel_application.model.enums.TripMessages.*;
import static az.travellab.ms_travel_application.util.DateUtil.DATE_UTIL;
import static java.time.LocalDate.now;
import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class ClientNotificationService {

    private final MessageService messageService;
    private final OfferRepository offerRepository;
    private final ClientRepository clientRepository;

    public void sendTripReminderOneDayBefore() {
        var offers = offerRepository.findByTripDateBetweenAndStatusAndServiceTypeIn(
                DATE_UTIL.toStartOfNextDay(), DATE_UTIL.toEndOfNextDay(), BOUGHT, List.of(AIR_TICKET, TOUR)
        );

        if (offers.isEmpty()) return;

        offers.forEach(
                offer -> {
                    var client = offer.getClient();
                    var managerPhone = client.getPhoneTo();
                    var message = REMINDER_TRIP_ONE_DAY_BEFORE.getMessage().formatted(
                            client.getNameFrom(),
                            DATE_UTIL.formatDateTimeLocalDateTime(offer.getTripDate()),
                            DATE_UTIL.formatDateTimeLocalDateTime(offer.getReturnDate()),
                            client.getNameTo(),
                            client.getPhoneTo()
                    );
                    prepareSendMessage(managerPhone, message, client);
                }
        );
    }

    private void prepareSendMessage(String managerPhone, String message, ClientEntity client) {
        messageService.sendMessage(
                managerPhone,
                message,
                singletonList(client.getPhoneFrom()), null
        );
    }

    public void sendTripReminder() {
        var offers = offerRepository.findByTripDateBetweenAndStatusAndServiceTypeIn(
                DATE_UTIL.toNowPlusHours(5),  DATE_UTIL.toNowPlusHours(6), BOUGHT, List.of(AIR_TICKET, TOUR)
        );

        if (offers.isEmpty()) return;

        offers.forEach(
                offer -> {
                    var client = offer.getClient();
                    var managerPhone = client.getPhoneTo();
                    var message = REMINDER_TRIP_DAY.getMessage().formatted(
                            client.getNameFrom(),
                            DATE_UTIL.formatDateTimeLocalDateTime(offer.getTripDate()),
                            DATE_UTIL.formatDateTimeLocalDateTime(offer.getReturnDate()),
                            client.getNameTo(),
                            client.getPhoneTo()
                    );
                    prepareSendMessage(managerPhone, message, client);
                }
        );
    }

    public void returnReminder() {
        var offers = offerRepository.findByReturnDateBetweenAndStatusAndServiceTypeIn(
                DATE_UTIL.toNowPlusHours(5),  DATE_UTIL.toNowPlusHours(6), BOUGHT, List.of(AIR_TICKET, TOUR)
        );

        if (offers.isEmpty()) return;

        offers.forEach(
                offer -> {
                    var client = offer.getClient();
                    var managerPhone = client.getPhoneTo();
                    var message = REMINDER_RETURN_DAY.getMessage().formatted(
                            client.getNameFrom(),
                            DATE_UTIL.formatDateTimeLocalDateTime(offer.getReturnDate()),
                            client.getNameTo(),
                            client.getPhoneTo()
                    );
                    prepareSendMessage(managerPhone, message, client);
                }
        );
    }

    public void initialPaymentDateReminder() {
        var offers = offerRepository.findByInitialPaymentDateBetweenAndStatusAndServiceTypeIn(
                DATE_UTIL.toStartOfDay(), DATE_UTIL.toEndOfDay(), BOUGHT, List.of(AIR_TICKET, TOUR)
        );

        if (offers.isEmpty()) return;

        offers.forEach(
                offer -> {
                    var client = offer.getClient();
                    var managerPhone = client.getPhoneTo();
                    var message = INITIAL_PAYMENT_DAY.getMessage().formatted(
                            client.getNameFrom(),
                            client.getNameTo(),
                            client.getPhoneTo()
                    );
                    prepareSendMessage(managerPhone, message, client);
                }
        );
    }

    public void paymentDateReminder() {
        var offers = offerRepository.findByPaymentDateBetweenAndStatusAndServiceTypeIn(
                DATE_UTIL.toStartOfDay(), DATE_UTIL.toEndOfDay(), BOUGHT, List.of(AIR_TICKET, TOUR)
        );

        if (offers.isEmpty()) return;

        offers.forEach(
                offer -> {
                    var client = offer.getClient();
                    var managerPhone = client.getPhoneTo();
                    var message = PAYMENT_DAY.getMessage().formatted(
                            client.getNameFrom(),
                            client.getNameTo(),
                            client.getPhoneTo()
                    );
                    prepareSendMessage(managerPhone, message, client);
                }
        );
    }

    public void sendBirthdayGreetings() {
        var today = now();
        var clients = clientRepository.findClientEntityByBirthMonthAndDay(today.getMonthValue(),today.getDayOfMonth());

        if (clients.isEmpty()) return;

        clients.forEach(
                client -> {
                    var message = BIRTH_DAY.getMessage().formatted(
                            client.getNameFrom(),
                            client.getNameTo(),
                            client.getPhoneTo()
                    );
                    prepareSendMessage(client.getPhoneTo(), message, client);
                }
        );
    }
}
