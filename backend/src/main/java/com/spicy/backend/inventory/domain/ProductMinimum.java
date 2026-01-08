package com.spicy.backend.inventory.domain;

import com.spicy.backend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductMinimum extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품ID
    @Column(nullable = false)
    private Long productId;

    //가맹점ID
    @Column(nullable = false)
    private Long storeId;

    //최소수량
    @Column(nullable = false)
    private int minimumQuantity;
}
