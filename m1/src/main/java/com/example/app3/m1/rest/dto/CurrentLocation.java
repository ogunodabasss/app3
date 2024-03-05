package com.example.app3.m1.rest.dto;

import com.example.app3.m1.utils.clazz.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public record CurrentLocation(
        @NotNull @Min(0) Long courierId,
        @NotNull @Valid Location location) {

}
