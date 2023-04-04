package com.food.ordering.system.order.service.data.access.order.adapter.mapper;

import com.food.ordering.system.order.service.data.access.customer.entity.CustomerEntity;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.system.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity){
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
