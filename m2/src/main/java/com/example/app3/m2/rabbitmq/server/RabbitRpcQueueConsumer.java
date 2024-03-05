package com.example.app3.m2.rabbitmq.server;

import com.example.app3.m2.service.PackageAmqpService;
import com.example.app3.shared.dtos.rabbitmq.CourierSharedPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)

@Validated
@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitRpcQueueConsumer {
    private final ConfigurableApplicationContext context;

    @RabbitListener(queues = "app3.m2.rpc", returnExceptions = "false")
    public Object rpcQueueConsumer(Message message, Channel channel) throws JsonProcessingException {
        log.info(".........rpcQueueConsumer:\n body: {}\n  message: {}\n Channel: {}\n", new String(message.getBody()), message, channel);

        final ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
        final String json = new String(message.getBody());

        final Object data = switch (message.getMessageProperties().getReceivedRoutingKey()) {
            case "m2.courierEndPackageDelivery" -> {
                CourierSharedPojo sharedPojo = objectMapper.readValue(json, CourierSharedPojo.class);
                yield context.getBean(PackageAmqpService.class).courierEndPackageDelivery(sharedPojo.getId());
            }
            case "m2.courierStartedPackageDelivery" -> {
                CourierSharedPojo sharedPojo = objectMapper.readValue(json, CourierSharedPojo.class);
                yield context.getBean(PackageAmqpService.class).courierStartedPackageDelivery(sharedPojo.getId(),sharedPojo.getPackageIds());
            }
            case "m2.findSelectLocationById" -> {
                Long packageId = Long.parseLong(json);
                yield context.getBean(PackageAmqpService.class).findSelectLocationById(packageId);
            }
            default ->
                    throw new IllegalStateException(STR."Unexpected value: \{message.getMessageProperties().getReceivedRoutingKey()}");
        };
        String res = objectMapper.writeValueAsString(data);
        log.info("MQ Response\ngetCorrelationId: {}\njson:{}\n",message.getMessageProperties().getCorrelationId(),res);
        return res;
    }
}
