package com.epam.electronic.repository;

import com.epam.electronic.model.Cart;
import com.epam.electronic.model.Order;
import com.epam.electronic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllProductsByCarts(Cart cart);
    List<Product> findAllProductsByOrders(Order order);

}

