package com.example.app3.m2.utils.clazz;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record Location(
        @NotNull @Min(-90) @Max(90) BigDecimal latitude,
        @NotNull @Min(-180) @Max(180) BigDecimal longitude) {

}
