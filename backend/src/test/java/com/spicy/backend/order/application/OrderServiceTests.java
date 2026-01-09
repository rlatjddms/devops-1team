package com.spicy.backend.order.application;

import com.spicy.backend.order.dao.OrderItemRepository;
import com.spicy.backend.order.dao.OrderRepository;
import com.spicy.backend.order.domain.Order;
import com.spicy.backend.order.dto.request.OrderCreateRequest;
import com.spicy.backend.order.dto.request.OrderItemRequest;
import com.spicy.backend.order.dto.request.wrapper.OrderAndOrderItemRequest;
import com.spicy.backend.order.dto.response.OrderCreateResponse;
import com.spicy.backend.order.dto.response.OrderResponse;
import com.spicy.backend.order.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTests {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    private Long userId;
    private Long orderId;
    private Long storeId;
    private Long productId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private Order order;
    private OrderAndOrderItemRequest orderAndOrderItemRequest;
    private OrderCreateRequest orderCreateRequest;
    private OrderItemRequest orderItemRequest;
    private List<OrderItemRequest> orderItemRequestList;
    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        userId = 1L;
        orderId = 10L;
        storeId = 100L;
        productId = 1000L;

        quantity = 10;

        unitPrice = BigDecimal.valueOf(100.00);

        order = Order.builder()
                .orderNumber("orderNumber")
                .storeId(storeId)
                .totalAmount(BigDecimal.valueOf(0))
                .status(Status.PENDING)
                .address("address")
                .receiverPhone("receiverPhone")
                .receiverName("receiverName")
                .memo("memo")
                .build();
        ReflectionTestUtils.setField(order, "id", orderId);

        orderList = List.of(order);
    }

    @Test
    @DisplayName("주문 생성 - 성공")
    public void createOrder_Success() {
        // given
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
                storeId,
                LocalDate.now(),
                "address",
                "receiverName",
                "receiverPhone",
                "memo"
        );

        List<OrderItemRequest> orderItemRequestList = List.of(
                new OrderItemRequest(
                        productId,
                        "productName",
                        quantity,
                        unitPrice
                )
        );

        OrderAndOrderItemRequest orderAndOrderItemRequest = new OrderAndOrderItemRequest(
                orderCreateRequest,
                orderItemRequestList
        );

        Order mockOrder = Order.builder()
                .orderNumber("orderNumber")
                .storeId(storeId)
                .totalAmount(BigDecimal.valueOf(0))
                .status(Status.PENDING)
                .address("address")
                .receiverPhone("receiverPhone")
                .receiverName("receiverName")
                .memo("memo")
                .build();
        ReflectionTestUtils.setField(mockOrder, "id", orderId);
        given(orderRepository.save(any(Order.class))).willReturn(mockOrder);

        given(orderItemRepository.saveAll(anyList())).willReturn(List.of());

        // when
        OrderCreateResponse response = orderService.createOrder(userId, orderAndOrderItemRequest);

        // then
        assertEquals(orderId, response.orderId());
    }


    @Test
    @DisplayName("주문 전체 조회 - 성공")
    void getOrders_Success() {
        // given
        given(orderRepository.findAllByStoreIdAndStatusOrderByCreatedAt(storeId, Status.PENDING)).willReturn(orderList);

        // when
        List<OrderResponse> response = orderService.getAllOrders(storeId, Status.PENDING);

        // then
        assertEquals(orderId, response.get(0).orderId());

        verify(orderRepository, times(1)).findAllByStoreIdAndStatusOrderByCreatedAt(storeId, Status.PENDING);
    }
}