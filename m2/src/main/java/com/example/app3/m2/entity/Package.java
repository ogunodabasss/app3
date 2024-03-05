package com.example.app3.m2.entity;

import com.example.app3.m2.entity.enums.PackageStatus;
import com.example.app3.m2.entity.mappedSuperclass.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})

@Entity
@Table
public class Package extends BaseEntity {


    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(referencedColumnName = "id", nullable = false)
    @NotNull
    private User user;

    @NotNull
    @Min(-90)
    @Max(90)
    @Column(nullable = false, precision = 18, scale = 15)
    private BigDecimal latitude;

    @NotNull
    @Min(-180)
    @Max(180)
    @Column(nullable = false, precision = 18, scale = 15)
    private BigDecimal longitude;

    @NotNull
    @Size(max = 400)
    @Column(nullable = false, length = 400)
    private String address;

    @Nullable
    @Column(nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private PackageStatus packageStatus;

    @Nullable
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime startDateTime;

    @Nullable
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private LocalDateTime endDateTime;

    @Nullable
    @Column()
    private Long courierId;


}
