package org.food.ordering.system.order.service.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.food.ordering.system.order.system.domain.entity.BaseEntity;
import org.food.ordering.system.order.system.domain.valueobject.Money;
import org.food.ordering.system.order.system.domain.valueobject.OrderId;
import org.food.ordering.system.order.service.domain.valueobject.OrderItemId;

@Setter
@Getter
@Builder
public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }
}
