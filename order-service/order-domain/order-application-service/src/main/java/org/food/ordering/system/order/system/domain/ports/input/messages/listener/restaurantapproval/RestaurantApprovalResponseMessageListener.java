package org.food.ordering.system.order.system.domain.ports.input.messages.listener.restaurantapproval;

import org.food.ordering.system.order.system.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
