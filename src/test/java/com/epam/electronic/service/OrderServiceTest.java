package com.epam.electronic.service;

import com.epam.electronic.model.Order;
import com.epam.electronic.model.Product;
import com.epam.electronic.model.User;
import com.epam.electronic.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order retrievedOrder = orderService.getOrderById(orderId);

        verify(orderRepository, times(1)).findById(orderId);

        assertNotNull(retrievedOrder);
        assertEquals(orderId, retrievedOrder.getId());
    }

    @Test
    public void testGetOrderByIdNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Assertion
        assertThrows(RuntimeException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    public void testGetOrdersByUser() {
        User user = new User();
        user.setId(1L);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());

        when(orderRepository.findByUser(user)).thenReturn(orders);

        // Call the service method
        List<Order> retrievedOrders = orderService.getOrdersByUser(user);

        // Assertions
        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
    }

    @Test
    public void testPlaceOrder() {
        User user = new User();
        user.setId(1L);

        Product product1 = new Product();
        product1.setPrice(99.99);
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setPrice(49.99);
        product2.setQuantity(5);

        List<Product> items = new ArrayList<>();
        items.add(product1);
        items.add(product2);

        Order placedOrder = orderService.placeOrder(user, items);

        verify(orderRepository, times(1)).save(any(Order.class));

        assertNotNull(placedOrder);
        assertEquals(user, placedOrder.getUser());
        assertEquals(items, placedOrder.getItems());
        assertNotNull(placedOrder.getOrderDate());
        assertTrue(placedOrder.getTotalPayment() > 0.0);
        assertEquals(9, product1.getQuantity());
        assertEquals(4, product2.getQuantity());
    }
}

