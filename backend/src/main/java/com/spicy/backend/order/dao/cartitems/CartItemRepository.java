package com.spicy.backend.order.dao.cartitems;

import com.spicy.backend.order.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUserIdAndStoreIdAndDeletedAtIsNull(Long userId, Long storeId);

    Optional<CartItem> findByUserIdAndId(Long userId, Long cartId);
}
