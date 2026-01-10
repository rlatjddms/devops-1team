package com.spicy.backend.demandplan.service;

import com.spicy.backend.demandplan.controller.dto.StockResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandPlanService {

    private final ExternalDataFetcher infoProvider;
    private static final double SAFETY_STOCK_RATE = 1.2;

    public void process(Long productId) {
        // 재고 및 유효 값 조회
        StockResponseDto stock = infoProvider.getStock(productId);

        //
        if(stock.totalQuantity() < stock.minimumQuantity()) {
            String recommendationMessage = createRecommendationMessage(productId);
            System.out.println("현재 재고는 " + stock.totalQuantity() +
                    "개 입니다. 추가 주문이 필요합니다." + recommendationMessage);
        } else {
            System.out.println("현재 재고가 충분합니다.");
        }
    }

    private String createRecommendationMessage(Long productId) {

        // 최근 7일간 재고 파악 후 권장 수량 계산 로직
        List<Integer> sales = infoProvider.getWeeklyOrderCount(productId);
        int totalSales = sales
                .stream()
                .mapToInt(i -> i).sum();

        // 최근 7일의 총 매출과 안전 수량 배율 상수를 곱연산하여 계산(배율 조정 가능)
        int recommended = (int)(totalSales * SAFETY_STOCK_RATE);

        return "추천 권장 수량은 " + recommended + "개 입니다.";
    }
}
