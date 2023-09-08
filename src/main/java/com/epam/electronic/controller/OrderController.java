package com.epam.electronic.controller;

import com.epam.electronic.dto.ProductIdsList;
import com.epam.electronic.model.Order;
import com.epam.electronic.model.Product;
import com.epam.electronic.model.User;
import com.epam.electronic.service.OrderService;
import com.epam.electronic.service.ProductService;
import com.epam.electronic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long userId, @RequestBody ProductIdsList productIds) {
        User user = userService.getUserById(userId);
        List<Product> items = productIds.getProductIds().stream()
                .map(productService::getProductById)
                .collect(Collectors.toList());
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Order order = orderService.placeOrder(user, items);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Order> orders = orderService.getOrdersByUser(user);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}

