package com.epam.electronic.service;

import com.epam.electronic.model.Order;
import com.epam.electronic.model.Product;
import com.epam.electronic.model.User;
import com.epam.electronic.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Order with id " + id + " is not found"));
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Order placeOrder(User user, List<Product> items) {
        Order order = new Order();
        order.setUser(user);
        order.setItems(items);
        order.setTotalPayment(items.stream()
                .map(Product::getPrice)
                .reduce(Double::sum)
                .orElseThrow(() -> new RuntimeException("Unable to count total payment")));
        order.setOrderDate(LocalDateTime.now());
        items.
                forEach(item -> {
                    if (item.getQuantity() > 0) {
                        item.setQuantity(item.getQuantity() - 1);
                    } else throw new RuntimeException("There is 0" + item + "in the stock");
                });
        orderRepository.save(order);
        return order;
    }
}

