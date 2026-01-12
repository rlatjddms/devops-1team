package com.spicy.backend.order.domain;

import com.spicy.backend.global.entity.BaseEntity;
import com.spicy.backend.global.error.errorcode.GlobalErrorCode;
import com.spicy.backend.global.error.exception.BusinessException;
import com.spicy.backend.order.dto.request.OrderItemRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fk: 주문 식별 번호
    @Column(nullable = false)
    private Long orderId;

    // fk: 상품 식별 번호
    @Column(nullable = false)
    private Long productId;

    // 상품 이름
    @Column(nullable = false)
    private String productName;

    // 주문 수량
    @Column(nullable = false)
    private Integer quantity;

    // 개별 단가
    @Column(nullable = false)
    private BigDecimal unitPrice;

    // 총 금액
    @Column(nullable = false)
    private BigDecimal totalPrice;

    public static OrderItem create(OrderItemRequest request) {
        return OrderItem.builder()
                .productId(request.productId())
                .productName(request.productName())
                .quantity(request.quantity())
                .unitPrice(request.unitPrice())
                .totalPrice(request.unitPrice().multiply(BigDecimal.valueOf(request.quantity())))
                .build();
    }

    public OrderItem updateOrderId(Long id) {
        if (id == null && id == 0) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT_VALUE);
        }
        this.orderId = id;

        return this;
    }
}
