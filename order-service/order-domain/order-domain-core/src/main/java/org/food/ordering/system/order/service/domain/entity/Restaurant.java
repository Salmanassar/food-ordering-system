package org.food.ordering.system.order.service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.food.ordering.system.order.system.domain.entity.AggregateRoot;
import org.food.ordering.system.order.system.domain.valueobject.RestaurantId;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Restaurant extends AggregateRoot<RestaurantId> {
    private final List<Product> products;

    private RestaurantId restaurantId;
    private boolean active;


    public boolean isActive() {
        return active;
    }

}
