package com.example.app3.shared.dtos.rabbitmq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder

@JsonIgnoreProperties({"handler", "fieldHandler"})
public class UserSharedPojo implements Serializable {
    @Nullable
    @Min(0)
    private Long id;

    @Nullable
    @Size(min = 3, max = 30)
    private String name;

    @Nullable
    @Size(min = 3, max = 30)
    private String surName;

    @Nullable
    @Size(min = 11, max = 11)
    private String tc;
}
