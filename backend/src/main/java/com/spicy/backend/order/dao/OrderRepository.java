package com.spicy.backend.order.dao;

import com.spicy.backend.order.domain.Order;
import com.spicy.backend.order.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStoreIdAndStatusOrderByCreatedAt(Long storeId, Status status);
}
