package com.spicy.backend.order.dao.cartitems;

import com.spicy.backend.order.domain.CartItem;

import java.util.List;

public interface CartItemRepositoryCustom {
    List<CartItem> findAllByUserIdAndStoreId(Long userId, Long storeId);
}
