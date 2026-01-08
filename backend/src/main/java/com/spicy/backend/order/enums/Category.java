package com.spicy.backend.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    // 신선 식품
    FRESH_FOOD("신선 식품"),

    // 가공 식품
    PROCESSED_FOOD("가공 식품"),

    // 냉동 식품
    FROZEN_FOOD("냉동 식품"),

    // 식기/용기
    GOODS("비품/용기"),

    // 음료
    BEVERAGE("음료");

    private final String description;
}
