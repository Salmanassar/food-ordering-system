package org.food.ordering.system.order.system.domain.dto.create;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderAddress {

    @NotNull
    @Max(value = 50)
    private final String street;

    @NotNull
    @Max(value = 10)
    private final String postalCode;

    @NotNull
    @Max(value = 50)
    private final String city;
}