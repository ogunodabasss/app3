package com.example.app3.m1.utils.clazz;

import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Sinks;

public record MapValue(@NotNull Sinks.Many<Location> sink, @NotNull Location locationPackage) {
}