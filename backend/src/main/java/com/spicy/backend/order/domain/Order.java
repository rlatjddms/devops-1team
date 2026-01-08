package com.spicy.backend.order.domain;

import com.spicy.backend.global.entity.BaseEntity;
import com.spicy.backend.order.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문번호
    @Column(nullable = false, unique = true)
    private String orderNumber;

    // 가맹점 pk
    @Column(nullable = false)
    private Long storeId;

    // 총 주문 금액
    @Column(nullable = false)
    private BigDecimal totalAmount;

    // 주문 상태
    @Column(nullable = false)
    private Status status;

    // 희망 배송일
    @Column(nullable = false)
    private LocalDate deliveryDate;

    // 주소
    @Column(nullable = false)
    private String address;

    // 수령인 이름
    @Column(nullable = false)
    private String receiverName;

    // 수령인 연락처
    @Column(nullable = false)
    private String receiverPhone;

    // 배송 요청사항
    @Column(nullable = false)
    private String memo;

    // 주문 일시 - createdAt
}
