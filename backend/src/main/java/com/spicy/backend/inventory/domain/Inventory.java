package com.spicy.backend.inventory.domain;

import com.spicy.backend.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Inventory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품ID
    private Long productId;

    //수량
    private int quantity;

    //유통기한
    private LocalDate expirationDate;

    //상품상태
    @Enumerated(EnumType.STRING)
    private LotStatus status;

}
