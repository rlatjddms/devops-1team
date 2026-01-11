package com.spicy.backend.inventory.storage;

import com.spicy.backend.inventory.domain.MinimumProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinimumProductRepository extends JpaRepository<MinimumProduct, Long> {
}
