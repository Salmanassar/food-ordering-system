package org.food.ordering.system.order.system.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public abstract class BaseEntity<V> {
    private V id;
}
