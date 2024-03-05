package com.example.app3.m2.config;

import com.example.app3.m2.service.PackageAmqpService;
import com.example.app3.m2.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

@Slf4j
@Configuration
public class RabbitMqConfig {

    //@Value("${message-brokers.rabbitmq.queue-names.m2.rpc}")
        public static final String QUEUE_NAME = "app3.m2.rpc";

    @Primary
    @Bean("queueRpc")
    public Queue queueRpc() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange(
            @Value("${message-brokers.rabbitmq.exchanges-name.direct.m2.PackageAmqpService}")
            String directExchangeName) {
        log.warn("directExchangeName: {} ", directExchangeName);
        return new DirectExchange(directExchangeName);
    }

    @Bean
    public Binding binding(
            DirectExchange exchange,
            @Qualifier("queueRpc") Queue queueRpc) {
        log.warn("binding1: \nqueueRpc:{}\nroutingKey:{} ", queueRpc.getName(), PackageAmqpService.ROUTING_KEY_courierStartedPackageDelivery);
        return BindingBuilder.bind(queueRpc).to(exchange).with(PackageAmqpService.ROUTING_KEY_courierStartedPackageDelivery);
    }

    @Bean
    public Binding binding2(
            DirectExchange exchange,
            @Qualifier("queueRpc") Queue queueRpc
    ) {
        log.warn("binding2: \nqueueRpc:{} \nroutingKey:{} ", queueRpc.getName(), PackageAmqpService.ROUTING_KEY_courierEndPackageDelivery);
        return BindingBuilder.bind(queueRpc).to(exchange).with(PackageAmqpService.ROUTING_KEY_courierEndPackageDelivery);
    }

    @Bean
    public Binding binding3(
            DirectExchange exchange,
            @Qualifier("queueRpc") Queue queueRpc
    ) {
        log.warn("binding3: \nqueueRpc:{} \nroutingKey:{} ", queueRpc.getName(), PackageAmqpService.ROUTING_KEY_findSelectLocationById);
        return BindingBuilder.bind(queueRpc).to(exchange).with(PackageAmqpService.ROUTING_KEY_findSelectLocationById);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    //@Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setUseDirectReplyToContainer(true);
        return rabbitTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtil.objectMapper();
    }

    private MessageConverter jsonMessageConverter() {
        var jsonConverter = new Jackson2JsonMessageConverter(objectMapper());

        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = Map.of(
                RemoteInvocationResult.class.getName(), RemoteInvocationResult.class
        );
        classMapper.setIdClassMapping(idClassMapping);

        jsonConverter.setClassMapper(classMapper);

        return new RemoteInvocationAwareMessageConverterAdapter(jsonConverter);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setMessageConverter(converter());

        factory.setErrorHandler(new ConditionalRejectingErrorHandler(new ConditionalRejectingErrorHandler.DefaultExceptionStrategy()));
        factory.setDefaultRequeueRejected(false);

        return factory;
    }

}
