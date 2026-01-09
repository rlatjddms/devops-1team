package com.spicy.backend.order.api;

import com.spicy.backend.global.common.ApiResponse;
import com.spicy.backend.order.application.CartItemService;
import com.spicy.backend.order.dto.response.CartItemResponse;
import com.spicy.backend.order.dto.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    // 장바구니 조회
    @Operation(summary = "장바구니 조회", description = "가맹점주가 장바구니에 추가한 목록 조회")
    @GetMapping("/{user-id}")
    public ResponseEntity<ApiResponse<List<CartItemResponse>>> getCartItems(
            @PathVariable("user-id") Long userId    // 가맹점주
    ) {

        return null;
    }

    // 장바구니에서 상품 삭제
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 선택한 상품들 삭제")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<ApiResponse<OrderResponse>> deleteCartItems(
            @PathVariable("user-id") Long userId    // 가맹점주
    ) {

        return null;
    }
}
