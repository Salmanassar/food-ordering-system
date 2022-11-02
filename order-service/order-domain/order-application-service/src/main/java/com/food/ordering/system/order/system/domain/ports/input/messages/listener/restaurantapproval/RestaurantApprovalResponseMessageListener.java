package com.food.ordering.system.order.system.domain.ports.input.messages.listener.restaurantapproval;

import com.food.ordering.system.order.system.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
