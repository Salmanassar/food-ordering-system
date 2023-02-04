package com.food.ordering.system.payment.service.domain.entity;

import com.food.ordering.system.order.system.domain.entity.BaseEntity;
import com.food.ordering.system.order.system.domain.valueobject.CustomerId;
import com.food.ordering.system.order.system.domain.valueobject.Money;
import com.food.ordering.system.payment.service.domain.valueobject.CreditEntryId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditEntry extends BaseEntity<CreditEntryId> {
    private final CustomerId customerId;
    private Money totalCreditAmount;


    private CreditEntry(Builder builder) {
        setId(builder.creditEntryId);
        customerId = builder.customerId;
        setTotalCreditAmount(builder.totalCreditAmount);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void addCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.addMoney(amount);
    }

    public void subtractCreditAmount(Money amount){
        totalCreditAmount = totalCreditAmount.subtractMoney(amount);
    }


    public static final class Builder {
        private CreditEntryId creditEntryId;
        private CustomerId customerId;
        private Money totalCreditAmount;

        private Builder() {
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public Builder creditEntryId(CreditEntryId val) {
            creditEntryId = val;
            return this;
        }

        public CreditEntry build() {
            return new CreditEntry(this);
        }
    }
}
