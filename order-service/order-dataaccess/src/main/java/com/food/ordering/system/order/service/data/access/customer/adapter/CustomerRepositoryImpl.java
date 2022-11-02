package com.food.ordering.system.order.service.data.access.customer.adapter;

import com.food.ordering.system.order.service.data.access.customer.mapper.CustomerDataAccessMapper;
import com.food.ordering.system.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.food.ordering.system.order.service.domain.entity.Customer;
import com.food.ordering.system.order.system.domain.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private CustomerJpaRepository customerJpaRepository;
    private CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository,
                                  CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findByTrackinId(customerId)
                .map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
