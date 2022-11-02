package com.food.ordering.system.order.system.domain;

import com.food.ordering.system.order.system.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.system.domain.mapper.OrderDataMapper;
import com.food.ordering.system.order.system.domain.ports.output.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.exception.OrderNotFoundDomainException;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class OrderTrackCommandHandler {
    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackId()));
        if (order.isEmpty()) {
            log.warn("Could not find oder with tracking id: ", trackOrderQuery.getOrderTrackId());
            throw new OrderNotFoundDomainException("Could not find oder with tracking id: " + trackOrderQuery.getOrderTrackId());
        }
        return orderDataMapper.orderToTrackOrderResponse(order.get());
    }
}
