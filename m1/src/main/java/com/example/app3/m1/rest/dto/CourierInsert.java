package com.example.app3.m1.rest.dto;

import com.example.app3.m1.entity.Courier;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public record CourierInsert(@NotNull @Size(min = 3, max = 30) String name,
                            @NotNull @Size(min = 3, max = 30) String surName,
                            @EqualsAndHashCode.Include
                            @NotNull @Size(min = 11, max = 11) String tc,
                            @NotNull Boolean work) {
    
    public static Courier toEntity(CourierInsert insert) {
        return Courier.builder()
                .id(null)
                .name(insert.name)
                .surName(insert.surName)
                .tc(insert.tc)
                .work(insert.work)
                .build();
    }
}
