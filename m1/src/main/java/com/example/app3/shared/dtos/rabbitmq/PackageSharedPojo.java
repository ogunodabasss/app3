package com.example.app3.shared.dtos.rabbitmq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder

@JsonIgnoreProperties({"handler", "fieldHandler"})
public class PackageSharedPojo implements Serializable {

    @Nullable
    @Min(-90)
    @Max(90)
    private final BigDecimal latitude;

    @Nullable
    @Min(-180)
    @Max(180)
    private final BigDecimal longitude;

    @Nullable
    @Size(max = 400)
    private final String address;

    @Nullable
    private final Byte packageStatus;

    @Nullable
    private Long id;

    @Nullable
    private LocalDateTime startDateTime;

    @Nullable
    private LocalDateTime endDateTime;

    @Nullable
    private Long courierId;

    private final UserSharedPojo user;
}
