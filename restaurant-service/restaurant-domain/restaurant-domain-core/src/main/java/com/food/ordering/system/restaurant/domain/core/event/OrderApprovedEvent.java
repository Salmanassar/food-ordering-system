package com.food.ordering.system.restaurant.domain.core.event;

import com.food.ordering.system.order.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.restaurant.domain.core.entity.OrderApproval;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderApprovedEvent extends OrderApprovalEvent{
    private final DomainEventPublisher<OrderApprovedEvent> orderApprovalEventDomainEventPublisher;
    public OrderApprovedEvent(OrderApproval orderApproval,
                              RestaurantId restaurantId,
                              List<String> failureMessages,
                              ZonedDateTime createdAt, DomainEventPublisher<OrderApprovedEvent> orderApprovalEventDomainEventPublisher) {
        super(orderApproval, restaurantId, failureMessages, createdAt);
        this.orderApprovalEventDomainEventPublisher = orderApprovalEventDomainEventPublisher;
    }

    @Override
    public void fire() {
orderApprovalEventDomainEventPublisher.publish(this);
    }
}
