package com.spicy.backend.order.application;

import com.spicy.backend.global.error.errorcode.GlobalErrorCode;
import com.spicy.backend.order.dao.OrderItemRepository;
import com.spicy.backend.order.dao.OrderRepository;
import com.spicy.backend.order.domain.Order;
import com.spicy.backend.order.domain.OrderItem;
import com.spicy.backend.order.dto.request.OrderItemRequest;
import com.spicy.backend.order.dto.request.wrapper.OrderAndOrderItemRequest;
import com.spicy.backend.order.dto.response.OrderCreateResponse;
import com.spicy.backend.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * Order 및 OrderItem 생성
     *
     * @param userId 사용자 id
     * @param request 주문 정보와 주문 상품 정보
     * @return 주문 번호 반환
     * @throws BusinessException(GlobalErrorCode.INVALID_INPUT_VALUE) 유저 정보 없음
     * */
    @Transactional
    public OrderCreateResponse createOrder(Long userId, OrderAndOrderItemRequest request) {
        // Order totalAmount 생성
        BigDecimal totalPrice = BigDecimal.ZERO;

        // Order 생성
        Order order = Order.create(request.orderCreateRequest());

        // orderId 받아오기 위해 먼저 저장
        orderRepository.save(order);

        // OrderItem 생성
        List<OrderItem> itemList = new ArrayList<>();
        for (OrderItemRequest item : request.orderItemRequestList()) {
            OrderItem orderItem = OrderItem.create(item);
            orderItem.updateOrderId(order.getId());

            totalPrice = totalPrice.add(orderItem.getTotalPrice());

            itemList.add(orderItem);
        }

        // Order totalAmount 업데이트
        order.update(totalPrice);

        // OrderItem 저장
        orderItemRepository.saveAll(itemList);

        return OrderCreateResponse.from(order.getId());
    }
}
