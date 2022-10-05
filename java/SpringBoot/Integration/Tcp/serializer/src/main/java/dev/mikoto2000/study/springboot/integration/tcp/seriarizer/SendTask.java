package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendTask {

    private final OutboundGateway og;

    public SendTask(OutboundGateway og) {
        this.og = og;
    }

    @Scheduled(fixedDelay = 500)
    public void task1() {
        System.out.println("task1");
        try {
            String result = this.og.send("test_task1");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error!!!!");
            e.printStackTrace();
        }
    }

}
