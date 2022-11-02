package com.food.ordering.system.order.service.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
@AllArgsConstructor
public class StreetAddress {
    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;
}
