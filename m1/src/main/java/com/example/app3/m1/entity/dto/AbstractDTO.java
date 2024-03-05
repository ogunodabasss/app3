package com.example.app3.m1.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter(onMethod = @__(@JsonProperty))
public abstract class AbstractDTO {
}