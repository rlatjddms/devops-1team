package com.spicy.backend.demandplan.service;

import com.spicy.backend.demandplan.controller.dto.StockResponseDto;

import java.util.List;

public interface ExternalDataFetcher {

    StockResponseDto getStock(Long productId);
    List<Integer> getRecentOrderCount(Long productId);
}
