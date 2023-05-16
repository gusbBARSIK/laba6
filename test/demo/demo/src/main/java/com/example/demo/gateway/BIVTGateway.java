package com.example.demo.gateway;

import com.example.demo.dto.BIVTDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class BIVTGateway {

    private final MessageChannel messageChannel;

    @Autowired
    public BIVTGateway(
            @Qualifier("personFilterChannel") MessageChannel messageChannel
    ) {
        this.messageChannel = messageChannel;
    }

    public void send(BIVTDto event) {
        messageChannel.send(MessageBuilder.withPayload(event).build());
    }

}