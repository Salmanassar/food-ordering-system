package org.food.ordering.system.order.service.domain.exception;

public class OrderNotFoundDomainException extends OrderDomainException{
    public OrderNotFoundDomainException(String message) {
        super(message);
    }
}
