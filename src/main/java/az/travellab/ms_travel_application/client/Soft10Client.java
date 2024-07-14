package az.travellab.ms_travel_application.client;

import az.travellab.ms_travel_application.client.decoder.CustomErrorDecoder;
import az.travellab.ms_travel_application.model.client.SendMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${url.base.soft-10}", name = "ms.soft10", configuration = CustomErrorDecoder.class)
public interface Soft10Client {

    @PostMapping("send")
    void sendMessage(@RequestHeader String secretkey, @RequestBody SendMessageRequest qrDetailsRequest);
}