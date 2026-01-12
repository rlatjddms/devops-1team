package com.spicy.backend.order.application;

import com.spicy.backend.order.dao.cartitems.CartItemRepository;
import com.spicy.backend.order.domain.CartItem;
import com.spicy.backend.order.dto.response.CartItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    public List<CartItemResponse> getCartItems(Long userId, Long storeId) {
        // CartItem 조회
        List<CartItem> cartItems = cartItemRepository.findAllByUserIdAndStoreId(userId, storeId);

        return CartItemResponse.from(cartItems);
    }
}
