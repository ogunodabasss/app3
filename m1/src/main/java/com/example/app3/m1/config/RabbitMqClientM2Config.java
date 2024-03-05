package com.example.app3.m1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class RabbitMqClientM2Config {

    private final ObjectMapper objectMapper;
    public static final String DIRECT_EXCHANGE_NAME = "app3.m1.PackageAmqpService.direct";

    static {

        log.info("DIRECT_EXCHANGE_NAME: {}",DIRECT_EXCHANGE_NAME);
    }
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setUseDirectReplyToContainer(true);
        return rabbitTemplate;
    }


    private MessageConverter jsonMessageConverter() {
        var jsonConverter = new Jackson2JsonMessageConverter(objectMapper);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = Map.of(
                RemoteInvocationResult.class.getName(), RemoteInvocationResult.class
        );
        classMapper.setIdClassMapping(idClassMapping);

        jsonConverter.setClassMapper(classMapper);

        return new RemoteInvocationAwareMessageConverterAdapter(jsonConverter);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
