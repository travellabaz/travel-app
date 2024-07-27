package az.travellab.ms_travel_application.client;

import az.travellab.ms_travel_application.client.decoder.CustomErrorDecoder;
import az.travellab.ms_travel_application.model.client.TelegramBotRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${url.base.telegram-bot}", name = "ms.telegram.bot", configuration = CustomErrorDecoder.class)
public interface TelegramBotClient {

    @Async
    @PostMapping
    void sendMessage(@RequestBody TelegramBotRequest telegramBotRequest);
}