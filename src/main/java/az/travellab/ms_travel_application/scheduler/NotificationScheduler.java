package az.travellab.ms_travel_application.scheduler;

import az.travellab.ms_travel_application.service.ClientNotificationService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationScheduler {

    private final ClientNotificationService clientNotificationService;

    @Async
    @Scheduled(fixedRateString = "PT15M")
//    @Scheduled(cron = "0 0 19 * * *")
    @SchedulerLock(name = "sendTripReminderOneDayBefore", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
    public void sendTripReminderOneDayBefore() {
        clientNotificationService.sendTripReminderOneDayBefore();
    }

//    @Async
//    @Scheduled(fixedRateString = "PT15M")
//    @SchedulerLock(name = "sendTripReminder", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
//    public void sendTripReminder() {
//        clientNotificationService.sendTripReminder();
//    }
//
//    @Async
//    @Scheduled(fixedRateString = "PT15M")
//    @SchedulerLock(name = "returnReminder", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
//    public void returnReminder() {
//        clientNotificationService.returnReminder();
//    }
//    @Async
//    @Scheduled(fixedRateString = "PT15M")
//    @SchedulerLock(name = "initialPaymentDateReminder", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
//    public void initialPaymentDateReminder() {
//        clientNotificationService.initialPaymentDateReminder();
//    }
//    @Async
//    @Scheduled(fixedRateString = "PT15M")
//    @SchedulerLock(name = "paymentDateReminder", lockAtLeastFor = "PT1M", lockAtMostFor = "PT5M")
//    public void paymentDateReminder() {
//        clientNotificationService.paymentDateReminder();
//    }
}