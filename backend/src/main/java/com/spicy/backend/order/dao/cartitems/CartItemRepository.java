package com.spicy.backend.order.dao.cartitems;

import com.spicy.backend.order.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUserIdAndStoreId(Long userId, Long storeId);
}
