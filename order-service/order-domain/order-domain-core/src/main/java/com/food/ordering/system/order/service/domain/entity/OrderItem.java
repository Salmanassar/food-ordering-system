package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.system.domain.entity.BaseEntity;
import com.food.ordering.system.order.system.domain.valueobject.Money;
import com.food.ordering.system.order.system.domain.valueobject.OrderId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;

    private OrderItemId orderItemId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }

    boolean isPriceValid() {
        return price.isGreaterThanZero() &&
                price.equals(product.getPrice()) &&
                price.multiply(quantity).equals(subTotal);
    }
}
