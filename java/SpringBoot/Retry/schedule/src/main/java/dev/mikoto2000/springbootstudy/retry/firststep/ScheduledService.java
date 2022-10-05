package dev.mikoto2000.springbootstudy.retry.firststep;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    private final RetryableService retryableService;

    public ScheduledService(RetryableService retryableService) {
        this.retryableService = retryableService;
    }

    @Scheduled(fixedRate = 5000)
    public void fixedRate5000() {
        System.out.println("start fixedRate5000");
        this.retryableService.hello("fixedRate5000");
    }
}
