package az.travellab.ms_travel_application.factory;

import az.travellab.ms_travel_application.model.client.TelegramBotRequest;
import az.travellab.ms_travel_application.model.enums.TelegramNotificationType;

public enum TelegramBotMapper {

    TELEGRAM_BOT_MAPPER;

    public TelegramBotRequest generateRequest(TelegramNotificationType notificationType, String salesNumber, String note) {
        return TelegramBotRequest
                .builder()
                .messageThreadIn(notificationType.getMessageThreadIn())
                .chatId(notificationType.getChatId())
                .text(String.format(notificationType.getText(), salesNumber, note))
                .build();
    }
}
