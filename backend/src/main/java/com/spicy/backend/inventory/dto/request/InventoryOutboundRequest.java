package com.spicy.backend.inventory.dto.request;

public record InventoryOutboundRequest(
        // 상품 ID
        Long id,
        // 상품 수량
        int quantity,
        // 적정 유통기한
        int monthsUntilExpiration
) {
}
