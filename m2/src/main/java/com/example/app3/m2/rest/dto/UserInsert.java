package com.example.app3.m2.rest.dto;

import com.example.app3.m2.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserInsert(@NotNull @Size(min = 3, max = 30) String name,
                         @NotNull @Size(min = 3, max = 30) String surName,
                         @NotNull @Size(min = 11, max = 11) String tc) {

    public static User toEntity(UserInsert insert) {
        return User.builder()
                .id(null)
                .name(insert.name)
                .surName(insert.surName)
                .tc(insert.tc)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInsert that)) return false;

        return tc().equals(that.tc());
    }

    @Override
    public int hashCode() {
        return tc().hashCode();
    }
}
