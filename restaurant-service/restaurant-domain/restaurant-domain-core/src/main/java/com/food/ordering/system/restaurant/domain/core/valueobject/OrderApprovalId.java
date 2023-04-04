package com.food.ordering.system.restaurant.domain.core.valueobject;

import com.food.ordering.system.order.system.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
