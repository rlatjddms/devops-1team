package com.spicy.backend.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    PENDING("PENDING", "접수 대기"),
    PROCESSING("PROCESSING", "처리중"),
    SHIPPED("SHIPPED", "배송중"),
    DELIVERED("DELIVERED", "도착"),
    CANCELLED("CANCELLED", "취소");

    private final String key;
    private final String value;
}
