package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.order.system.domain.entity.BaseEntity;
import com.food.ordering.system.order.system.domain.valueobject.CustomerId;
import com.food.ordering.system.order.system.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;
import com.food.ordering.system.payment.service.domain.valueobject.TransactionalType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreditHistory extends BaseEntity<CreditHistoryId> {

    private final CustomerId customerId;
    private final Money amount;
    private final TransactionalType transactionalType;


    private CreditHistory(Builder builder) {
        setId(builder.creditHistoryId);
        customerId = builder.customerId;
        amount = builder.amount;
        transactionalType = builder.transactionalType;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private CustomerId customerId;
        private Money amount;
        private TransactionalType transactionalType;
        private CreditHistoryId creditHistoryId;

        private Builder() {
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder transactionalType(TransactionalType val) {
            transactionalType = val;
            return this;
        }

        public Builder creditHistoryId(CreditHistoryId val) {
            creditHistoryId = val;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}
