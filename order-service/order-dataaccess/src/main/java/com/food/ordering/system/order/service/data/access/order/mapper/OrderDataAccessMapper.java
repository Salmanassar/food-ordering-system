package com.food.ordering.system.order.service.data.access.order.mapper;

import com.food.ordering.system.order.service.data.access.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.data.access.order.entity.OrderEntity;
import com.food.ordering.system.order.service.data.access.order.entity.OrderItemEntity;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import com.food.ordering.system.order.system.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryOrderToAdressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntity(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMassages() != null ?
                        String.join(",", order.getFailureMassages()) : "")
                .build();
        orderEntity.getAddress().setOrderEntity(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .deliveryAddress(orderAddressEntityToStreetAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntityToOrderItem(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMassages(orderEntity.getFailureMessages().isEmpty() ?
                        new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(","))))
                .build();
    }

    private StreetAddress orderAddressEntityToStreetAddress(OrderAddressEntity orderAddressEntity) {
        return new StreetAddress(orderAddressEntity.getId(),
                orderAddressEntity.getStreet(),
                orderAddressEntity.getPostalCode(),
                orderAddressEntity.getCity());
    }

    private List<OrderItem> orderItemEntityToOrderItem(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .product(new Product(new ProductId(orderItemEntity.getProductId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(toList());

    }

    private List<OrderItemEntity> orderItemsToOrderItemEntity(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build())
                .collect(toList());
    }

    private OrderAddressEntity deliveryOrderToAdressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .city(deliveryAddress.getCity())
                .postalCode(deliveryAddress.getPostalCode())
                .street(deliveryAddress.getStreet())
                .build();
    }
}
