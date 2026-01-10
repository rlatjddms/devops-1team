package com.spicy.backend.demandplan.adapter;

import com.spicy.backend.demandplan.controller.dto.StockResponseDto;
import com.spicy.backend.demandplan.service.ExternalDataFetcher;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ExternalDataFetcherImpl implements ExternalDataFetcher {

    @Override
    public StockResponseDto getStock(Long productId) {
        // TODO: Inventory 서비스 코드 구현시 작성 예정
        return null;
    }

    @Override
    public List<Integer> getWeeklyOrderCount(Long productId) {
        // TODO: Settlement 서비스 코드 구현시 작성 예정
        return List.of();
    }
}
