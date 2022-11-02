package com.food.ordering.system.order.system.domain.mapper;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.system.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.system.domain.dto.create.OrderItem;
import com.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.system.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {
    public Restaurant createRestaurantOrderCommand(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getCustomerId()))
                .products(createOrderCommand.getOrderItems().stream()
                        .map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
                        .toList()).build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(this.orderItemsToOrderItemEntity(createOrderCommand.getOrderItems())).build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackerId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .massage(message).build();
    }

    private List<com.food.ordering.system.order.service.domain.entity.OrderItem> orderItemsToOrderItemEntity(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItemsDTO -> com.food.ordering.system.order.service.domain.entity.OrderItem.builder()
                        .orderId(new OrderId(orderItemsDTO.getProductId()))
                        .product(new Product(new ProductId(orderItemsDTO.getProductId())))
                        .price(new Money(orderItemsDTO.getPrice()))
                        .quantity(orderItemsDTO.getQuantity())
                        .subTotal(new Money(orderItemsDTO.getSubTotal()))
                        .build())
                .toList();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder().orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMassages(order.getFailureMassages())
                .build();
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(UUID.randomUUID(), orderAddress.getStreet(), orderAddress.getPostalCode(), orderAddress.getCity());
    }
}
