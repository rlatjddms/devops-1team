package com.spicy.backend.demandplan.controller;

import org.springframework.context.annotation.Configuration;

public record StockResponseDto(
        Long productId,
        Integer currentStock,
        Integer safetyStock
) {}
