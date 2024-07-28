package az.travellab.ms_travel_application.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TelegramNotificationType {
    SALE_UPDATE_NOTIFICATION("13", "-1002195775797", "UPDATE: \nNumber: %s \nNote: %s"),
    LAST_PAYMENT_DATE_NOTIFICATION(null, null, null),
    INITIAL_PAYMENT_DATE_NOTIFICATION(null, null, null),
    VISA_NOTIFICATION(null, null, null);

    private final String messageThreadIn;
    private final String chatId;
    private final String text;
}
