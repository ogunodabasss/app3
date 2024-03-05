package com.example.app3.m1.rest;

import com.example.app3.m1.entity.Courier;
import com.example.app3.m1.rest.dto.CourierInsert;
import com.example.app3.m1.rest.dto.CourierStartedPackageDelivery;
import com.example.app3.m1.rest.dto.CurrentLocation;
import com.example.app3.m1.rest.dto.CurrentLocationResponse;
import com.example.app3.m1.service.CourierApiService;
import com.example.app3.m1.service.CourierService;
import com.example.app3.m1.service.CurrentLocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/courier")
public class CourierRestController {

    private final CourierService service;
    private final CourierApiService courierApiService;
    private final CurrentLocationService currentLocationService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Courier> insert(@Valid @RequestBody CourierInsert insert) {
        return ResponseEntity.ok(service.insert(CourierInsert.toEntity(insert)));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Courier>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object[] courierStartedPackageDelivery(@Valid @RequestBody CourierStartedPackageDelivery data) {
        return courierApiService.courierStartedPackageDelivery(data.id(), data.packageIds());
    }

    @PostMapping(value = "/end", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object[]> courierEndPackageDelivery(@Valid @NotNull @Min(0) @RequestBody Long id) {
        return ResponseEntity.ok(courierApiService.courierEndPackageDelivery(id));
    }

    @PostMapping(value = "/currentLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public void currentLocation(@Valid @RequestBody CurrentLocation currentLocation) {
        currentLocationService.publisher(currentLocation);
    }

    @GetMapping(path = "/currentLocation", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CurrentLocationResponse> currentLocation(@RequestParam @NotNull @Min(0) Long id) {
        currentLocationService.receiverBeforeCreating(id);
        return Objects.requireNonNull(currentLocationService.receiver(id))
                .map(location -> currentLocationService.function(id, location));
    }

}
