package com.example.app3.m1.entity.mappedSuperclass;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {
}
