package dev.mikoto2000.study.springboot.integration.tcp.serializer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Request;
import dev.mikoto2000.study.springboot.integration.tcp.serializer.model.Response;

@Component
public class SendTask {

    private byte i = 0;

    private final OutboundGateway og;

    public SendTask(OutboundGateway og) {
        this.og = og;
    }

    @Scheduled(fixedDelay = 5000)
    public void task1() {
        System.out.println("task1");
        try {
            var request = new Request((short)1, new byte[]{0, 1, 2, 3, i});
            Response result = this.og.send(request);
            i++;
            System.out.println("result: " + result);
        } catch (Exception e) {
            System.out.println("Error!!!!");
            e.printStackTrace();
        }
    }

}
