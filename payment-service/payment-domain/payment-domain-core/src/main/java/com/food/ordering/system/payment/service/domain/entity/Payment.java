package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.order.system.domain.entity.AggregateRoot;
import com.food.ordering.system.order.system.domain.valueobject.CustomerId;
import com.food.ordering.system.order.system.domain.valueobject.Money;
import com.food.ordering.system.order.system.domain.valueobject.OrderId;
import com.food.ordering.system.order.system.domain.valueobject.PaymentStatus;
import com.food.ordering.system.payment.service.domain.valueobject.PaymentId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Payment extends AggregateRoot<PaymentId> {

    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money price;

    private PaymentId paymentId;
    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    private Payment(Builder builder) {
        setId(builder.paymentId);
        orderId = builder.orderId;
        customerId = builder.customerId;
        price = builder.price;
        setPaymentId(builder.paymentId);
        setPaymentStatus(builder.paymentStatus);
        setCreatedAt(builder.createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializePayment(){
        setId(new PaymentId(UUID.randomUUID()));
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public void validatePayment(List<String> failureMessage){
        if(price==null||!price.isGreaterThanZero()){
             failureMessage.add("Total price must be greater then zero!");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus){
        this.paymentStatus = paymentStatus;
    }


    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private Money price;
        private PaymentId paymentId;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder paymentId(PaymentId val) {
            paymentId = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
