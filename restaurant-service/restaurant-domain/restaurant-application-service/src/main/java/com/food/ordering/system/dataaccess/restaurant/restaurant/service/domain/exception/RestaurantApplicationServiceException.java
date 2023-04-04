package com.food.ordering.system.dataaccess.restaurant.restaurant.service.domain.exception;

import com.food.ordering.system.order.system.domain.exception.DomainException;

public class RestaurantApplicationServiceException extends DomainException {
    public RestaurantApplicationServiceException(String message) {
        super(message);
    }

    public RestaurantApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
