package org.food.ordering.system.order.system.domain.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.food.ordering.system.order.system.domain.valueobject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderResponse {

    @NotNull
    private final UUID orderTrackingId;

    @NotNull
    private final OrderStatus orderStatus;

    List<String> failureMassages;
}
