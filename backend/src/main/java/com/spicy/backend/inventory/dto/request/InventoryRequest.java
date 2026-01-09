package com.spicy.backend.inventory.dto.request;

import java.time.LocalDate;

public record InventoryRequest(
        // 상품ID
        Long productId,
        // 상품 수량
        int quantity,
        // 유통기한
        LocalDate expirationDate
) {
}
