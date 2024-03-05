package com.example.app3.m1.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper = true)
@ToString(onlyExplicitlyIncluded = true,callSuper = true)
@Data
@Getter(onMethod = @__(@JsonProperty))
public class BaseCourierDTO extends  AbstractDTO {
}
