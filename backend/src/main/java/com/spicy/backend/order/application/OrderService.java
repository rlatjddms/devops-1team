package com.spicy.backend.order.application;

import com.spicy.backend.global.error.exception.BusinessException;
import com.spicy.backend.order.dao.cartitems.CartItemRepository;
import com.spicy.backend.order.dao.order.OrderItemRepository;
import com.spicy.backend.order.dao.order.OrderRepository;
import com.spicy.backend.order.domain.CartItem;
import com.spicy.backend.order.domain.Order;
import com.spicy.backend.order.domain.OrderItem;
import com.spicy.backend.order.dto.request.OrderCreateRequest;
import com.spicy.backend.order.dto.response.OrderCanceledResponse;
import com.spicy.backend.order.dto.response.OrderCreateResponse;
import com.spicy.backend.order.dto.response.OrderItemResponse;
import com.spicy.backend.order.dto.response.OrderResponse;
import com.spicy.backend.order.enums.Status;
import com.spicy.backend.order.error.CartItemErrorCode;
import com.spicy.backend.order.error.OrderErrorCode;
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
    private final CartItemRepository cartItemRepository;

    /**
     * Order 및 OrderItem 생성
     *
     * @param storeId 가맹점 식별 번호
     * @param userId 사용자 식별 번호
     * @param request 주문 정보
     * @return 주문 번호 반환
     * @throws BusinessException(CartItemErrorCode.CART_ITEM_NOT_FOUND) 유저 정보 없음
     * */
    @Transactional
    public OrderCreateResponse createOrder(Long storeId, Long userId, OrderCreateRequest request) {
        // 사용자 검증 및 장바구니 가져오기
        List<CartItem> cartList = cartItemRepository.findAllByUserIdAndStoreIdAndDeletedAtIsNull(userId, storeId);
        if (cartList.isEmpty()) throw new BusinessException(CartItemErrorCode.CART_ITEM_NOT_FOUND);

        // Order 생성 및 저장
        Order order = orderRepository.save(Order.create(request, storeId));

        // OrderItem 생성 및 저장
        createAndSaveOrderItems(cartList, order);

        // 장바구니에서 삭제
        cartItemRepository.deleteAll(cartList);

        return OrderCreateResponse.from(order.getId());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders(Long storeId, Status status) {
        // status에 따라 주문 리스트 조회
        List<Order> orders = orderRepository.findAllByStoreIdAndStatusAndDeletedAtIsNullOrderByCreatedAtDesc(storeId, status);

        // 리스트 반환
        return OrderResponse.from(orders);
    }

    @Transactional(readOnly = true)
    public List<OrderItemResponse> getOrderDetails(Long storeId, Long orderId) {
        // storeId, orderId로 OrderItem 리스트 조회
        // 리스트 길이가 0일 때 예외 발생
        List<OrderItem> itemList = orderItemRepository.findAllByStoreIdAndOrderIdAndDeletedAtIsNullOrderByCreatedAtDesc(storeId, orderId);
        if (itemList.isEmpty()) throw new BusinessException(OrderErrorCode.ORDER_ITEM_NOT_FOUND);

        // OrderItem 리스트를 OrderItemResponse 리스트로 변환 후 반환
        return OrderItemResponse.from(itemList);
    }

    @Transactional(rollbackFor = BusinessException.class)
    public OrderCanceledResponse cancelOrder(Long storeId, Long orderId) {
        // 사용자 검증
        Order order = orderRepository.findByStoreIdAndIdAndDeletedAtIsNull(storeId, orderId)
                .orElseThrow(() -> new BusinessException(OrderErrorCode.ORDER_NOT_FOUND));
        List<OrderItem> items = orderItemRepository.findAllByStoreIdAndOrderIdAndDeletedAtIsNullOrderByCreatedAtDesc(storeId, orderId);

        // 주문과 주문 상품의 상태를 취소로 변경
        order.updateStatus(Status.CANCELLED);
        for (OrderItem orderItem : items) {
            orderItem.updateStatus(Status.CANCELLED);
        }

        return OrderCanceledResponse.from(order, items);
    }

    public void createAndSaveOrderItems(
            List<CartItem> cartList,
            Order order
    ) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> itemList = new ArrayList<>();

        for (CartItem item : cartList) {
            OrderItem orderItem = OrderItem.create(item);
            orderItem.updateOrderId(order.getId());

            order.updateTotalPrice(totalPrice.add(orderItem.getTotalPrice()));

            itemList.add(orderItem);
        }

        // OrderItem 저장
        orderItemRepository.saveAll(itemList);
    }
}
