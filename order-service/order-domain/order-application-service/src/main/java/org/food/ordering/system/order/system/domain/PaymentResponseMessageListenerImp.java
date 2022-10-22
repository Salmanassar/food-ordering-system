package org.food.ordering.system.order.system.domain;

import org.food.ordering.system.order.system.domain.dto.message.PaymentResponse;
import org.food.ordering.system.order.system.domain.ports.input.messages.listener.payment.PaymentResponseMessageListener;

public class PaymentResponseMessageListenerImp implements PaymentResponseMessageListener {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}
