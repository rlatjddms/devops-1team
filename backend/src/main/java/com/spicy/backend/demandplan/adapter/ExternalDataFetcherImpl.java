package com.spicy.backend.demandplan.adapter;

import com.spicy.backend.demandplan.controller.dto.StockResponseDto;
import com.spicy.backend.demandplan.error.DemandPlanErrorCode;
import com.spicy.backend.demandplan.service.ExternalDataFetcher;
import com.spicy.backend.global.error.exception.BusinessException;
import com.spicy.backend.inventory.application.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExternalDataFetcherImpl implements ExternalDataFetcher {

    private final InventoryService inventoryService;

    @Override
    public StockResponseDto getStock(Long productId) {

        var inventoryData = inventoryService.searchProduct(productId);

        if(inventoryData == null) {
            throw new BusinessException(DemandPlanErrorCode.FAILED_TO_FETCH_PRODUCTS);
        }

        return new StockResponseDto(
                inventoryData.productName(),
                inventoryData.price(),
                inventoryData.minimumQuantity(),
                inventoryData.totalQuantity(),
                inventoryData.products()
        );
    }

    @Override
    public List<Integer> getRecentOrderCount(Long productId, int month) {
        // TODO: Settlement 서비스 코드 구현시 작성 예정
        return List.of();
    }
}
