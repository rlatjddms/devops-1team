package com.spicy.backend.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryOutboundRequest(
        // 상품 ID
        @NotNull(message = "상품 ID는 필수입니다.")
        Long id,
        // 상품 수량
        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        int quantity,
        // 적정 유통기한
        @NotNull(message = "희망 유통기한 날짜는 필수입니다.")
        int monthsUntilExpiration
) {
}
