package com.food.ordering.system.order.service.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.food.ordering.system.order.service.domain.entity.Customer;
import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.Product;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.exception.OrderDomainException;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.system.domain.dto.create.OrderAddress;
import org.food.ordering.system.order.system.domain.dto.create.OrderItem;
import org.food.ordering.system.order.system.domain.ports.input.service.OrderApplicationService;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.system.domain.mapper.OrderDataMapper;
import org.food.ordering.system.order.system.domain.ports.output.repository.CustomerRepository;
import org.food.ordering.system.order.system.domain.ports.output.repository.OrderRepository;
import org.food.ordering.system.order.system.domain.ports.output.repository.RestaurantRepository;
import org.food.ordering.system.order.system.domain.valueobject.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;
    private final UUID CUSTOM_ID = UUID.randomUUID();
    private final UUID RESTAURANT_ID = UUID.randomUUID();
    private final UUID PRODUCT_ID = UUID.randomUUID();
    private final UUID ORDER_ID = UUID.randomUUID();
    private final BigDecimal PRICE = new BigDecimal("200.00");

    public OrderApplicationServiceTest() {
    }

    @BeforeAll
    public void init() {
        this.createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOM_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("street1").city("city1")
                        .postalCode("12345").build())
                .price(PRICE)
                .orderItems(List.of(
                        OrderItem.builder()
                        .productId(this.PRODUCT_ID)
                        .quantity(1)
                        .price(new BigDecimal("50.00"))
                        .subTotal(new BigDecimal("50.00")).build(),
                        OrderItem.builder()
                                .productId(this.PRODUCT_ID)
                                .quantity(3).price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00")).build()))
                .build();
        this.createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(this.CUSTOM_ID)
                .restaurantId(this.RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("street1")
                        .city("Paris")
                        .postalCode("12345")
                        .build())
                .price(new BigDecimal("250.00"))
                .orderItems(List.of(OrderItem.builder()
                        .productId(this.PRODUCT_ID)
                        .quantity(1).
                        price(new BigDecimal("50.00")).
                        subTotal(new BigDecimal("50.00"))
                        .build(),
                        OrderItem.builder().productId(this.PRODUCT_ID)
                                .quantity(3).price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        this.createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(this.CUSTOM_ID).
                restaurantId(this.RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("street1").city("city1")
                        .postalCode("12345").build())
                .price(new BigDecimal("210.00"))
                .orderItems(List.of(OrderItem.builder()
                        .productId(this.PRODUCT_ID)
                        .quantity(1)
                        .price(new BigDecimal("60.00"))
                        .subTotal(new BigDecimal("60.00")).build(),
                        OrderItem.builder().productId(this.PRODUCT_ID)
                                .quantity(3).price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        Customer customer = new Customer();
        customer.setId(new CustomerId(this.CUSTOM_ID));
        Restaurant restaurantResponse = Restaurant.builder().restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(new Product(new ProductId(this.PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(this.PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true).build();
        Order order = this.orderDataMapper.createOrderCommandToOrder(this.createOrderCommand);
        order.setId(new OrderId(this.ORDER_ID));
        Mockito.when(this.customerRepository.findCustomer(this.CUSTOM_ID)).thenReturn(Optional.of(customer));
        Mockito.when(this.restaurantRepository.findRestaurantInformation(this.orderDataMapper.createRestaurantOrderCommand(this.createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        Mockito.when(this.orderRepository.save((Order) ArgumentMatchers.any(Order.class))).thenReturn(order);
    }

    @Test
    public void createOrderTest() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(createOrderResponse.getOrderStatus(), OrderStatus.PENDING);
        assertEquals(createOrderResponse.getMassage(), "Order is committed successfully");
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        OrderDomainException orderDomainException = (OrderDomainException) assertThrows(OrderDomainException.class, () -> {
            this.orderApplicationService.createOrder(this.createOrderCommandWrongPrice);
        });
        assertEquals(orderDomainException.getMessage(), "Total price 250.00 is not equals to 200.00");
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = (OrderDomainException) assertThrows(OrderDomainException.class, () -> {
            this.orderApplicationService.createOrder(this.createOrderCommandWrongProductPrice);
        });
        assertEquals(orderDomainException.getMessage(), "Order item price 60.00 is not valid for product " + this.PRODUCT_ID);
    }

    @Test
    public void testCreateOderWithPassiveRestaurant() {
        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(false)
                .build();
        Mockito.when(restaurantRepository.findRestaurantInformation(orderDataMapper.createRestaurantOrderCommand(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> {
            orderApplicationService.createOrder(createOrderCommand);
        });
        assertEquals(orderDomainException.getMessage(), "Restaurant with id " + RESTAURANT_ID + " is currently not active");
    }
}

