package com.example.app3.m1.rest.dto;

import com.example.app3.m1.utils.clazz.Location;
import jakarta.validation.constraints.NotNull;


public record CurrentLocationResponse(@NotNull Location location, double km) {

}
