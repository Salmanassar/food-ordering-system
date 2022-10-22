package org.food.ordering.system.order.system.domain.dto.create;

import lombok.*;
import org.food.ordering.system.order.system.domain.valueobject.Money;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {

    @NotNull
    private final UUID productId;

    @NotNull
    private final Integer quantity;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final BigDecimal subTotal;
}
