package com.spicy.backend.inventory.dto.response;

import com.spicy.backend.inventory.domain.LotStatus;

import java.time.LocalDate;

public record InventoryLotResponse(
    // 상품ID
    Long productId,
    // 수량
    int quantity,
    // 유통기한
    LocalDate expirationDate,
    // 상품 상태
    LotStatus status,
    // 상품 일련번호
    String productCode
){
}
