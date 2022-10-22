package org.food.ordering.system.order.service.domain.entity;

import org.food.ordering.system.order.system.domain.entity.BaseEntity;
import org.food.ordering.system.order.system.domain.valueobject.Money;
import org.food.ordering.system.order.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public Product(ProductId productId, String s, Money money) {
    }

    public String getName() {
        return this.name;
    }

    public Money getPrice() {
        return this.price;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final Money price) {
        this.price = price;
    }

    public Product(final String name, final Money price) {
        this.name = name;
        this.price = price;
    }
}
