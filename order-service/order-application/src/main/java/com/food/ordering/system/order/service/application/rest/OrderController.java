package com.food.ordering.system.order.service.application.rest;

import com.food.ordering.system.order.system.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.system.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.system.domain.dto.track.TrackOrderQuery;
import com.food.ordering.system.order.system.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.system.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order for customer id: {} at restaurant id: {}",
                createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Created order for customer id: {} at restaurant id: {}",
                createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        return ResponseEntity.ok(createOrderResponse);
    }

    @GetMapping(value = "/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrderResponse =
                orderApplicationService.trackOrder(TrackOrderQuery.builder()
                        .orderTrackId(trackingId)
                        .build());
        log.info("Returning order status with tracking id: {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }
}
