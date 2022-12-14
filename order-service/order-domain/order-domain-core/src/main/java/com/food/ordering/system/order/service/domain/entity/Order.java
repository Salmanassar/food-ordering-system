package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.order.system.domain.entity.AggregateRoot;
import com.food.ordering.system.order.system.domain.valueobject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMassages;

    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateItemsPrice() {
        Money totalItemsPrice = items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::addMoney);
        if (!price.getAmount().equals(totalItemsPrice.getAmount())) {
            throw new OrderDomainException("Total price " + price.getAmount()
                    + " is not equals to " + totalItemsPrice.getAmount());
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price " + orderItem.getPrice().getAmount()
                    + " is not valid for product " + orderItem.getOrderId().getValue());
        }
    }

    private void validateTotalPrice() {
        if (price == null || !getPrice().isGreaterThanZero()) {
            throw new OrderDomainException("Total price must be greater than zero!");
        }
    }

    private void validateInitialOrder() {
        if (orderStatus != null || getId() != null) {
            throw new OrderDomainException("Order is not correct state for initialization!");
        }
    }

    private void initializeOrderItems() {
        long itemId = 1l;
        for (OrderItem orderItem : items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not correct state for pay operation");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approved() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException("Order is not correct for approved operation");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMassages) {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not correct for initCancel operation");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMassage(failureMassages);
    }

    public void cancel(List<String> failureMassages) {
        if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
            throw new OrderDomainException("Order is not correct for cancel operation");
        }
        orderStatus = OrderStatus.CANCELED;
        updateFailureMassage(failureMassages);
    }

    private void updateFailureMassage(List<String> failureMassages) {
        if (this.failureMassages != null && failureMassages != null) {
            this.failureMassages.addAll(failureMassages.stream().filter(massage -> !massage.isEmpty()).toList());
        }
        if (this.failureMassages == null) {
            this.failureMassages = failureMassages;
        }
    }
}
