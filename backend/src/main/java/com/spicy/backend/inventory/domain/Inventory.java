package com.spicy.backend.inventory.domain;

import com.spicy.backend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Inventory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품ID
    @Column(nullable = false)
    private Long productId;

    //수량
    @Column(nullable = false)
    private int quantity;

    //유통기한
    @Column(nullable = false)
    private LocalDate expirationDate;

    //상품상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LotStatus status;

}
