package com.food.ordering.system.restaurant.domain.core.event;

import com.food.ordering.system.order.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.core.entity.OrderApproval;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderRejectedEvent extends OrderApprovalEvent{
    private final DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher;
    public OrderRejectedEvent(OrderApproval orderApproval,
                              RestaurantId restaurantId,
                              List<String> failureMessages,
                              ZonedDateTime createdAt,
                              DomainEventPublisher<OrderRejectedEvent> orderRejectedlEventDomainEventPublisher) {
        super(orderApproval, restaurantId, failureMessages, createdAt);
        this.orderRejectedEventDomainEventPublisher = orderRejectedlEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        orderRejectedEventDomainEventPublisher.publish(this);
    }
}
