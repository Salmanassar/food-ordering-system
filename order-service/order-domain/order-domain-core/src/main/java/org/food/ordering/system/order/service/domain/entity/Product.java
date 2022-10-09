package org.food.ordering.system.order.service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.food.ordering.system.order.system.domain.entity.BaseEntity;
import org.food.ordering.system.order.system.domain.valueobject.Money;
import org.food.ordering.system.order.system.domain.valueobject.ProductId;
@Getter
@Setter
@AllArgsConstructor
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public void updateWithConfirmedNameAnaPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
