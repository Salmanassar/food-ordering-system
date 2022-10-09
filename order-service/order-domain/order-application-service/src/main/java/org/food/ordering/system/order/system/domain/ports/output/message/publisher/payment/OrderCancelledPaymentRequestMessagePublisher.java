package org.food.ordering.system.order.system.domain.ports.output.message.publisher.payment;

import org.food.ordering.system.order.system.domain.event.publisher.DomainEventPublisher;
import org.food.ordering.system.order.service.domain.event.OrderCanceledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCanceledEvent> {
}
