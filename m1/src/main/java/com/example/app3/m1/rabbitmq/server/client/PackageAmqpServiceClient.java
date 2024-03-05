package com.example.app3.m1.rabbitmq.server.client;

import com.example.app3.m1.config.RabbitMqClientM2Config;
import com.example.app3.m1.utils.SpringAmqpUtils;
import com.example.app3.m1.utils.clazz.Location;
import com.example.app3.shared.dtos.rabbitmq.CourierSharedPojo;
import com.example.app3.shared.dtos.rabbitmq.PackageSharedPojo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class PackageAmqpServiceClient {
    public static final String ROUTING_KEY_courierStartedPackageDelivery = "m2.courierStartedPackageDelivery";
    public static final String ROUTING_KEY_courierEndPackageDelivery = "m2.courierEndPackageDelivery";
    public static final String ROUTING_KEY_findSelectLocationById = "m2.findSelectLocationById";
    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;

    public Location findSelectLocationById(Long id) {
        try {
            Object res = template.convertSendAndReceive(
                    RabbitMqClientM2Config.DIRECT_EXCHANGE_NAME,
                    ROUTING_KEY_findSelectLocationById,
                    id);
            log.info("findSelectLocationById: {}", res);

            SpringAmqpUtils.consumerReturnExceptionsHandle(res);
            return objectMapper.readValue((String) res, Location.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PackageSharedPojo courierEndPackageDelivery(Long id) {
        try {
            Object res = template.convertSendAndReceive(
                    RabbitMqClientM2Config.DIRECT_EXCHANGE_NAME,
                    ROUTING_KEY_courierEndPackageDelivery,
                    CourierSharedPojo.builder().id(id).build());
            log.info("courierEndPackageDelivery: {}", res);

            SpringAmqpUtils.consumerReturnExceptionsHandle(res);
            return objectMapper.readValue((String) res, PackageSharedPojo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<PackageSharedPojo> courierStartedPackageDelivery(
            Long id,
            Set<Long> packageIds) {
        try {
            var data = CourierSharedPojo.builder()
                    .id(id)
                    .build();
            data.addAllPackageId(packageIds);
            log.info("courierStartedPackageDelivery: data{}", data);

            Object res = template.convertSendAndReceive(
                    RabbitMqClientM2Config.DIRECT_EXCHANGE_NAME,
                    ROUTING_KEY_courierStartedPackageDelivery,
                    data);
            log.info("\ncourierStartedPackageDelivery: res:{}", res);

            SpringAmqpUtils.consumerReturnExceptionsHandle(res);

            return objectMapper.readValue((String) res, objectMapper.getTypeFactory().constructCollectionType(Set.class, PackageSharedPojo.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
