package az.travellab.ms_travel_application.scheduler;

import az.travellab.ms_travel_application.service.ClientNotificationService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final ClientNotificationService clientNotificationService;

//    @Scheduled(fixedRateString = "PT15M")
//    public void sendTripReminderOneDayBefore() {
//        clientNotificationService.sendTripReminderOneDayBefore();
//    }
}