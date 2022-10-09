package org.food.ordering.system.order.system.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.system.domain.dto.track.TrackOrderQuery;
import org.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;
import org.food.ordering.system.order.system.domain.ports.input.service.OderApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OderApplicationService {
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
