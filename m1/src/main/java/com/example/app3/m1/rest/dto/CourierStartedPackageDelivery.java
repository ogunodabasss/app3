package com.example.app3.m1.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CourierStartedPackageDelivery(
        @NotNull @Min(0) Long id,
        @NotNull.List(@NotNull) @NotEmpty Set<@NotNull @Min(0) Long> packageIds) {
}
