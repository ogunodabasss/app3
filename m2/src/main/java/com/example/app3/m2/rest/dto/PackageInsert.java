package com.example.app3.m2.rest.dto;

import com.example.app3.m2.entity.Package;
import com.example.app3.m2.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;


public record PackageInsert(@NotNull @Min(-90) @Max(90) BigDecimal latitude,
                            @NotNull @Min(-180) @Max(180) BigDecimal longitude,
                            @NotNull @Size(max = 400) String address,
                            @NotNull Long userId) {


    public static Package toEntity(PackageInsert insert) {
        return Package.builder()
                .id(null)
                .latitude(insert.latitude)
                .longitude(insert.longitude)
                .address(insert.address)
                .user(User.builder().id(insert.userId).build())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackageInsert that)) return false;
        return Objects.equals(userId, that.userId);
    }

    public int hashCode() {
        return Objects.hash(userId);
    }
}
