package com.food.ordering.system.payment.service.domain.event;

import com.food.ordering.system.order.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCancelledEvent extends PaymentEvent {

    private DomainEventPublisher<PaymentCancelledEvent> paymentCancelledDomainEventPublisher;

    public PaymentCancelledEvent(Payment payment,
                                 ZonedDateTime createdAt,
                                 DomainEventPublisher<PaymentCancelledEvent> paymentCancelledDomainEventPublisher) {
        super(payment, createdAt, Collections.emptyList());
        this.paymentCancelledDomainEventPublisher = paymentCancelledDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentCancelledDomainEventPublisher.publish(this);
    }
}
