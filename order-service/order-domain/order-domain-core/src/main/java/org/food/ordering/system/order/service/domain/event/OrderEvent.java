package org.food.ordering.system.order.service.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.food.ordering.system.order.system.domain.event.DomainEvent;
import org.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime zonedDateTime;
}
