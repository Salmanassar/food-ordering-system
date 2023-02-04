package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.order.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentFailedEvent extends PaymentEvent {

    private final DomainEventPublisher<PaymentFailedEvent> paymentFailedDomainEventPublisher;

    public PaymentFailedEvent(Payment payment,
                              ZonedDateTime createdAt,
                              DomainEventPublisher<PaymentFailedEvent> paymentFailedDomainEventPublisher) {
        super(payment, createdAt, Collections.emptyList());
        this.paymentFailedDomainEventPublisher = paymentFailedDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentFailedDomainEventPublisher.publish(this);
    }
}
