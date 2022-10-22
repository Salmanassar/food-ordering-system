//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.food.ordering.system.order.service.domain;

import org.food.ordering.system.order.service.domain.OrderDomainService;
import org.food.ordering.system.order.service.domain.OrderDomainServiceImpl;

import org.food.ordering.system.order.system.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import org.food.ordering.system.order.system.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import org.food.ordering.system.order.system.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import org.food.ordering.system.order.system.domain.ports.output.repository.CustomerRepository;
import org.food.ordering.system.order.system.domain.ports.output.repository.OrderRepository;
import org.food.ordering.system.order.system.domain.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"org.food.ordering.system.order"}
)
public class OrderTestConfiguration {
    public OrderTestConfiguration() {
    }

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return (OrderRepository) Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
