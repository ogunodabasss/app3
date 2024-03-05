package com.example.app3.m1.entity;

import com.example.app3.m1.entity.embadedable.CourierPackage;
import com.example.app3.m1.entity.mappedSuperclass.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})

@Entity
@Table
public class Courier extends BaseEntity {

    @ElementCollection(fetch = FetchType.EAGER)
    private final Set<CourierPackage> courierPackages = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
    private String surName;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(nullable = false, length = 11, unique = true)
    private String tc;

    @NotNull
    @Column(nullable = false)
    private Boolean work;

    @JsonIgnore
    public void addAllPackageId(Collection<CourierPackage> courierPackages) {
        this.courierPackages.addAll(courierPackages);
    }

    @JsonIgnore()
    public void addCourierPackage(@NotNull CourierPackage packageId) {
        courierPackages.add(packageId);
    }

    @JsonIgnore
    public void removeCourierPackage(CourierPackage courierPackage) {
        courierPackages.remove(courierPackage);
    }

    @JsonIgnore
    public boolean removeAllCourierPackage(Set<CourierPackage> courierPackages) {
        return this.courierPackages.removeAll(courierPackages);
    }
}
