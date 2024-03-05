package com.example.app3.m1.service;

import com.example.app3.m1.rabbitmq.server.client.PackageAmqpServiceClient;
import com.example.app3.m1.rest.dto.CurrentLocation;
import com.example.app3.m1.rest.dto.CurrentLocationResponse;
import com.example.app3.m1.utils.clazz.Location;
import com.example.app3.m1.utils.clazz.MapValue;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrentLocationService {

    public static final Map<Long, MapValue> MAP = new TreeMap<>();
    private final ConfigurableApplicationContext context;

    public void publisher(CurrentLocation currentLocation) {
        if (MAP.containsKey(currentLocation.courierId())) {
            var mapValue = MAP.get(currentLocation.courierId());
            final Sinks.@NotNull Many<Location> sink = mapValue.sink();
            sink.emitNext(currentLocation.location(), (signalType, emitResult) -> (signalType == SignalType.ON_NEXT || signalType == SignalType.ON_COMPLETE) && emitResult.isSuccess());
        }
        log.warn("publisher: {}", MAP);
    }

    public void receiverBeforeCreating(long courierId) {
        if (!MAP.containsKey(courierId)) {
            var sink = Sinks.many().replay().<Location>limit(Duration.of(5, ChronoUnit.SECONDS));

            var packageId = context.getBean(CourierService.class).findCourierPackagesSelectPackageIdById(courierId);
            var locationPackage = context.getBean(PackageAmqpServiceClient.class).findSelectLocationById(packageId);
            var mapValue = new MapValue(sink, locationPackage);
            MAP.put(courierId, mapValue);
        }
        log.warn("receiverBeforeCreating: {}", MAP);
    }


    public @Nullable Flux<Location> receiver(long courierId) {
        @Nullable var mapValue = MAP.get(courierId);
        if (mapValue != null) return mapValue.sink().asFlux();
        else return null;
    }

    public CurrentLocationResponse function(long courierId, Location location) {
        var locationPackage = MAP.get(courierId).locationPackage();
        double p1 = Math.abs(locationPackage.longitude().remainder(location.longitude()).doubleValue());
        double p2 = Math.abs(locationPackage.latitude().remainder(location.latitude()).doubleValue());

        return new CurrentLocationResponse(location, Math.hypot(p1, p2));
    }

    public void complete(long courierId) {
        if (MAP.containsKey(courierId)) {
            var sink = MAP.get(courierId).sink();
            sink.emitComplete((_, _) -> true);
            MAP.remove(courierId);
        }
        log.warn("complete: {}", MAP);
    }

}

