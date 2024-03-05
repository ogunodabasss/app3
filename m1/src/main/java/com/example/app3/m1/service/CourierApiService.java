package com.example.app3.m1.service;

import com.example.app3.m1.entity.Courier;
import com.example.app3.m1.entity.dto.CourierDTO;
import com.example.app3.m1.entity.embadedable.CourierPackage;
import com.example.app3.m1.rabbitmq.server.client.PackageAmqpServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class CourierApiService {

    private final CourierService service;
    private final PackageAmqpServiceClient packageAmqpServiceClient;
    private final CurrentLocationService currentLocationService;


    public Object[] courierEndPackageDelivery(Long id) {
        var valid = service.findById(id, CourierDTO.class);
        if (!valid.work())
            throw new RuntimeException(STR."work=\{Boolean.FALSE}");
        var courier = Courier.builder()
                .id(id)
                .work(Boolean.FALSE)
                .build();
        var res1 = service.update(courier);
        log.info("courierEndPackageDelivery res1: {}", res1);
        var res2 = packageAmqpServiceClient.courierEndPackageDelivery(id);
        log.info("courierEndPackageDelivery res2: {}", res2);
        currentLocationService.complete(id);
        return new Object[]{res1, res2};
    }

    public Object[] courierStartedPackageDelivery(Long id, Set<Long> packageIds) {
        var valid = service.findById(id, CourierDTO.class);
        if (valid.work())
            throw new RuntimeException(STR."work=\{Boolean.TRUE}");
        var courier = Courier.builder()
                .id(id)
                .work(Boolean.TRUE)
                .build();
        courier.addAllPackageId(packageIds.stream().map(aLong -> CourierPackage.builder()
                .packageId(aLong)
                .insertDate(LocalDateTime.now())
                .build()
        ).collect(Collectors.toUnmodifiableSet()));

        var res1 = service.update(courier);
        log.info("courierStartedPackageDelivery res1: {}", res1);

        var res2 = packageAmqpServiceClient.courierStartedPackageDelivery(id, packageIds);
        log.info("courierStartedPackageDelivery res2: {}", res2);

        return new Object[]{res1, res2};
    }
}
