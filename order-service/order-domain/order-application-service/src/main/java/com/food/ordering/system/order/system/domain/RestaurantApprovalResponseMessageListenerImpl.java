package com.food.ordering.system.order.system.domain;

import com.food.ordering.system.order.system.domain.dto.message.RestaurantApprovalResponse;
import com.food.ordering.system.order.system.domain.ports.input.messages.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantApprovalResponseMessageListenerImpl.class);

    public RestaurantApprovalResponseMessageListenerImpl() {
    }

    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
    }

    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
    }
}
