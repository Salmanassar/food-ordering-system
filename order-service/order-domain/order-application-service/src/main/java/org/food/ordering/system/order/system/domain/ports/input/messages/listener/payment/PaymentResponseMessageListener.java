package org.food.ordering.system.order.system.domain.ports.input.messages.listener.payment;

import org.food.ordering.system.order.system.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
