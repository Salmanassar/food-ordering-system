package org.food.ordering.system.order.system.domain.ports.input.service;

import org.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import org.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.system.domain.dto.track.TrackOrderQuery;
import org.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
