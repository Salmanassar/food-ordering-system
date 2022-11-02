package com.food.ordering.system.order.service.data.access.restaurant.adapter;

import com.food.ordering.system.order.service.data.access.restaurant.entity.RestaurantEntity;
import com.food.ordering.system.order.service.data.access.restaurant.mapper.RestaurantDataAccessMapper;
import com.food.ordering.system.order.service.data.access.restaurant.repository.RestaurantJpaRepository;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.system.domain.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private RestaurantJpaRepository restaurantJpaRepository;
    private RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProductIds = restaurantDataAccessMapper.restaurantToRestaurantProduct(restaurant);
        Optional<List<RestaurantEntity>> restaurantEntities =
                restaurantJpaRepository.findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(),
                        restaurantProductIds);
        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
