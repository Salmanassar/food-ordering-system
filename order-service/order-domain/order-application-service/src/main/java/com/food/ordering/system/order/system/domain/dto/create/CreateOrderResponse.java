package com.food.ordering.system.order.system.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import com.food.ordering.system.order.system.domain.valueobject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {

    @NotNull
    private final UUID orderTrackerId;

    @NotNull
    private final OrderStatus orderStatus;

    @NotNull
    private final String massage;

}
