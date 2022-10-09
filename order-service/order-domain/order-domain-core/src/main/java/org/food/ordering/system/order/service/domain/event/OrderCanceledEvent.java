package org.food.ordering.system.order.service.domain.event;

import org.food.ordering.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;


public class OrderCanceledEvent extends OrderEvent {

    public OrderCanceledEvent(Order order, ZonedDateTime zonedDateTime) {
        super(order, zonedDateTime);
    }
}
