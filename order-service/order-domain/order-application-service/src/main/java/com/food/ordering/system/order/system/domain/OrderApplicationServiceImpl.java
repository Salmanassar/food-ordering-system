package com.food.ordering.system.order.system.domain;

import com.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.system.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;
import lombok.extern.slf4j.Slf4j;
import com.food.ordering.system.order.system.domain.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
