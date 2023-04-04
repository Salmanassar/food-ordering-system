package com.food.ordering.system.order.service.data.access.customer.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_custome_m_view", schema = "customer")
public class CustomerEntity {

    @Id
    private UUID id;
}
