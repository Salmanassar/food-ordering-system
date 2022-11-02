package com.food.ordering.system.order.system.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public abstract class BaseId<T> {
    private final T value;
}
