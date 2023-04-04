package com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain;

import com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.dto.RestaurantApprovalRequest;
import com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.ports.output.message.publiser.OrderApprovedMessagePublisher;
import com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.ports.output.message.publiser.OrderRejectedMessagePublisher;
import com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.ports.output.repository.RestaurantRepository;
import com.food.ordering.system.order.system.domain.valueobject.OrderId;
import com.food.ordering.system.restaurant.domain.core.RestaurantDomainService;
import com.food.ordering.system.restaurant.domain.core.entity.Restaurant;
import com.food.ordering.system.restaurant.domain.core.event.OrderApprovalEvent;
import com.food.ordering.system.restaurant.domain.core.exception.RestaurantNotFoundException;
import lombok.extern.slf4j.Slf4j;
import com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.mapper.RestaurantDataMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class RestaurantApprovalRequestHelper {
    private final RestaurantDomainService restaurantDomainService;
    private final RestaurantDataMapper restaurantDataMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    public RestaurantApprovalRequestHelper(RestaurantDomainService restaurantDomainService,
                                           RestaurantDataMapper restaurantDataMapper,
                                           RestaurantRepository restaurantRepository,
                                           OrderApprovalRepository orderApprovalRepository,
                                           OrderApprovedMessagePublisher orderApprovedMessagePublisher,
                                           OrderRejectedMessagePublisher orderRejectedMessagePublisher) {
        this.restaurantDomainService = restaurantDomainService;
        this.restaurantDataMapper = restaurantDataMapper;
        this.restaurantRepository = restaurantRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderApprovedMessagePublisher = orderApprovedMessagePublisher;
        this.orderRejectedMessagePublisher = orderRejectedMessagePublisher;
    }

    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Process restaurant approval for order id: {}", restaurantApprovalRequest.getOrderId());
        Restaurant restaurant = findRestaurant(restaurantApprovalRequest);
        List<String> failureMessages = new ArrayList<>();
        OrderApprovalEvent orderApprovalEvent = restaurantDomainService.validateOrder(restaurant, failureMessages,
                orderApprovedMessagePublisher, orderRejectedMessagePublisher);
        orderApprovalRepository.save(restaurant.getOrderApproval());
        return orderApprovalEvent;
    }

    private Restaurant findRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        Restaurant restaurant = restaurantDataMapper.restaurantApprovalRequestToRestaurant(restaurantApprovalRequest);
        Optional<Restaurant> restaurantResult = restaurantRepository.findRestaurantInformation(restaurant);
        if(restaurantResult.isEmpty()){
            log.error("Restaurant with id: {}  not found!", restaurant.getId().getValue());
            throw new RestaurantNotFoundException("Restaurant with id: " + restaurant.getId().getValue() + " not found!");
        }
        Restaurant restaurantEntity = restaurantResult.get();
        restaurant.setActive(restaurantEntity.isActive());
        restaurant.getOrderDetail().getProducts().forEach(product -> {
            restaurantEntity.getOrderDetail().getProducts().forEach(p -> {
                if (p.getId().equals(product.getId())) {
                    product.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
                }
            });
            restaurant.getOrderDetail().setId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())));
        });

        return restaurant;
    }
}