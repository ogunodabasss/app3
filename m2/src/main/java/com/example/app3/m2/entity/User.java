package com.example.app3.m2.entity;

import com.example.app3.m2.entity.mappedSuperclass.BaseEntity;
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
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})

@Entity
@Table(name = "\"user\"")
public class User extends BaseEntity {


    @ToString.Include
    @EqualsAndHashCode.Include
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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user", cascade = {CascadeType.ALL})
    @NotNull
    private final Set<Package> packages = new HashSet<>();

    @JsonIgnore
    public void addPackage(@NotNull Package p) {
        packages.add(p);
    }

    @JsonIgnore
    public void removePackage(Package p) {
        packages.remove(p);
    }

    @JsonIgnore
    public boolean removeAllPackage(Collection<Package> packages) {
        return this.packages.removeAll(packages);
    }
}
