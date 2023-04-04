package com.food.ordering.system.order.service.data.access.restaurant.mapper;

import com.food.ordering.system.dataaccess.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.system.domain.valueobject.Money;
import com.food.ordering.system.order.system.domain.valueobject.ProductId;
import com.food.ordering.system.order.system.domain.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantDataAccessMapper {
    public List<UUID> restaurantToRestaurantProduct(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .toList();
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurants) {
        RestaurantEntity restaurantEntity = restaurants.stream()
                .findFirst().orElseThrow(() -> new RestaurantDataAccessException("The restaurant could not be found"));
        List<Product> products = restaurants.stream().map(entity ->
                        new Product(new ProductId(entity.getProductId()), entity.getProductName(),
                                new Money(entity.getProductPrice())))
                .toList();
        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(products)
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}
