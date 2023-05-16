package com.example.demo.activator;

import com.example.demo.dto.BIVTDto;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class BIVTActivator {
    @ServiceActivator(inputChannel = "personInboundChannel")
    public void activate(Message<BIVTDto> event) {

        System.out.println(event.getPayload().getFullName());
        System.out.println(event.getPayload().getBirthday());
        System.out.println(event.getPayload().getRandomValue());
    }
}

