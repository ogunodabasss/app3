package com.example.app3.m2.service;

import com.example.app3.m2.entity.Package;
import com.example.app3.m2.entity.enums.PackageStatus;
import com.example.app3.m2.utils.clazz.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

@Validated
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class PackageAmqpService {


    public static final String ROUTING_KEY_courierStartedPackageDelivery = "m2.courierStartedPackageDelivery";

    public static final String ROUTING_KEY_courierEndPackageDelivery = "m2.courierEndPackageDelivery";
    public static final String ROUTING_KEY_findSelectLocationById = "m2.findSelectLocationById";

    private final PackageService service;

    public Location findSelectLocationById(@Valid @NotNull @Min(0) Long id) {
       var p = service.findById(id).orElseThrow();
       return new Location(p.getLatitude(),p.getLongitude());
    }

    public Package courierEndPackageDelivery(@Valid @NotNull @Min(0) Long id) {

        var p = Package.builder()
                .id(id)
                .packageStatus(PackageStatus.DELIVERED)
                .endDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                .build();
        return service.update(p);
    }

    public Iterable<Package> courierStartedPackageDelivery(@NotNull @Min(0) Long courierId, @NotNull @NotEmpty Set<@Min(0) Long> ids) {
        if (ids.size() > 1) {
            List<Package> updateEntityList = ids.stream()
                    .map(aLong -> (Package) Package.builder()
                            .id(aLong)
                            .courierId(courierId)
                            .packageStatus(PackageStatus.DISTRIBUTION_COMPANY)
                            .startDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                            .endDateTime(null)
                            .build()
                    ).toList();
            return service.updateAll(updateEntityList);
        } else {
            return Set.of(service.update(Package.builder()
                    .id(ids.iterator().next())
                    .courierId(courierId)
                    .packageStatus(PackageStatus.DISTRIBUTION_COMPANY)
                    .startDateTime(LocalDateTime.now(ZoneId.systemDefault()))
                    .endDateTime(null)
                    .build()));
        }
    }
}
