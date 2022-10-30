package org.food.ordering.system.order.system.domain.mapper;

import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.OrderItem;
import org.food.ordering.system.order.service.domain.entity.Product;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.system.domain.dto.create.OrderAddress;
import org.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;
import org.food.ordering.system.order.system.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class OrderDataMapper {
    public Restaurant createRestaurantOrderCommand(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getCustomerId()))
                .products(createOrderCommand.getOrderItems().stream()
                        .map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
                        .collect(toList())).build();
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

    private List<OrderItem> orderItemsToOrderItemEntity(List<org.food.ordering.system.order.system.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItemsDTO -> OrderItem.builder()
                .orderId(new OrderId(orderItemsDTO.getProductId()))
                .product(new Product(new ProductId(orderItemsDTO.getProductId())))
                .price(new Money(orderItemsDTO.getPrice()))
                .quantity(orderItemsDTO.getQuantity())
                .subTotal(new Money(orderItemsDTO.getSubTotal()))
                .build())
                .collect(toList());
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
