package com.example.app3.shared.dtos.rabbitmq;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder

@JsonIgnoreProperties({"handler", "fieldHandler"})
public class CourierSharedPojo implements Serializable {
    @Nullable
    @EqualsAndHashCode.Include
    private final Long id;

    @Nullable
    @Size(min = 3, max = 30)
    private final String name;

    @Nullable
    @Size(min = 3, max = 30)
    private final String surName;

    @Nullable
    @Size(min = 11, max = 11)
    private final String tc;

    @Nullable
    private final Boolean work;

    @NotNull
    private final Set<@NotNull @Min(0) Long> packageIds = new HashSet<>();

    @JsonIgnore
    public void addAllPackageId(Set<Long> packageIds) {
        this.packageIds.addAll(packageIds);
    }

    @JsonIgnore
    public void addPackageId(@NotNull Long packageId) {
        packageIds.add(packageId);
    }

    @JsonIgnore
    public void removePackageId(Long packageId) {
        packageIds.remove(packageId);
    }

    @JsonIgnore
    public boolean removeAllPackageId(Set<Long> packageIds) {
        return this.packageIds.removeAll(packageIds);
    }
}
