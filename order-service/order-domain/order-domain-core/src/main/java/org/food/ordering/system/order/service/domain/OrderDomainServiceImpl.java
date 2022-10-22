package org.food.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.service.domain.entity.Order;
import org.food.ordering.system.order.service.domain.entity.Product;
import org.food.ordering.system.order.service.domain.entity.Restaurant;
import org.food.ordering.system.order.service.domain.event.OrderCanceledEvent;
import org.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import org.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import org.food.ordering.system.order.service.domain.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order wit ID {} is instantiated ", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("order with id :{} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approved();
        log.info("The order wit id: {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCanceledEvent cancelOrderPayment(Order order, List<String> failureMassages) {
        order.initCancel(failureMassages);
        log.info("The order with id: {} is canceled for payment", order.getId().getValue());
        return new OrderCanceledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMassages) {
        order.cancel(failureMassages);
        log.info("The order with id: {} is cancelling", order.getId().getValue());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue()
                    + " is currently not active");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
//        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
//            Product currentProduct = orderItem.getProduct();
//            currentProduct.updateWithConfirmedNameAnaPrice(restaurantProduct.getName(),
//                    restaurantProduct.getPrice());
//        }));
    }
}
