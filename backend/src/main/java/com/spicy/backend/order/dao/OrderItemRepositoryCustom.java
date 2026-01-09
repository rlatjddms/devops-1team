package com.spicy.backend.order.dao;

import com.spicy.backend.order.domain.OrderItem;

import java.util.List;

public interface OrderItemRepositoryCustom {
    List<OrderItem> findAllByStoreIdAndOrderId(Long storeId, Long orderId);
}
