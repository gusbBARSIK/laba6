package com.example.demo.configuration;

import com.example.demo.dto.BIVTDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.redis.inbound.RedisQueueMessageDrivenEndpoint;
import org.springframework.integration.redis.outbound.RedisQueueOutboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


@Configuration
@EnableIntegration
public class RedisIntegrationConfiguration {
    private static final String QUEUE_HELLO_WORLD = "queue:person";

    @Bean("personInboundChannelFlow")
    public IntegrationFlow redisPersonEventInboundChannelFlow(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("personInboundChannel") MessageChannel channel
    ) {
        RedisQueueMessageDrivenEndpoint endpoint =
                new RedisQueueMessageDrivenEndpoint(QUEUE_HELLO_WORLD, redisConnectionFactory);
        Jackson2JsonRedisSerializer<BIVTDto> serializer
                = new Jackson2JsonRedisSerializer<>(BIVTDto.class);

        endpoint.setSerializer(serializer);
        endpoint.setBeanName("personRedisQueueMessageDrivenEndpoint");

        return IntegrationFlows
                .from(endpoint)
                .channel(channel)
                .get();
    }

    @Bean("personOutboundChannelFlow")
    public IntegrationFlow redisPersonEventOutboundChannelFlow(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("personOutboundChannel") MessageChannel channel
    ) {
        Jackson2JsonRedisSerializer<BIVTDto> serializer
                = new Jackson2JsonRedisSerializer<>(BIVTDto.class);

        RedisQueueOutboundChannelAdapter channelAdapter =
                new RedisQueueOutboundChannelAdapter(QUEUE_HELLO_WORLD, redisConnectionFactory);
        channelAdapter.setSerializer(serializer);
        return IntegrationFlows
                .from(channel)
                .handle(channelAdapter)
                .get();
    }

    @Bean("personOutboundChannel")
    public SubscribableChannel personOutboundChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.setMaxSubscribers(3);
        channel.setComponentName("personOutboundChannel");
        return channel;
    }

    @Bean("personFilterChannel")
    public SubscribableChannel personFilterChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.setComponentName("personFilterChannel");
        return channel;
    }

    @Bean("personInboundChannel")
    public SubscribableChannel personInboundChannel() {
        PublishSubscribeChannel channel = new PublishSubscribeChannel();
        channel.setMaxSubscribers(3);
        channel.setComponentName("personInboundChannel");
        return channel;
    }
}
