package com.example.app3.m2.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.NotNull;

public enum PackageStatus {

    NOT_DELIVERED,
    DELIVERED,
    DISTRIBUTION_COMPANY,
    ON_THE_WAY,
    ADDRESS_INCORRECT;

    @JsonValue
    public byte value() {
        return (byte) super.ordinal();
    }

    @Override
    public @NotNull String toString() {
        return String.valueOf(super.ordinal());
    }
}
