package com.epam.electronic.service;

import com.epam.electronic.model.Cart;
import com.epam.electronic.model.Product;
import com.epam.electronic.repository.CartRepository;
import com.epam.electronic.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Cart with id " +  id + " is not found"));
    }

    public void addToCart(Cart cart, Product product) {
        cart.getCartItems().add(product);
        cartRepository.save(cart);
    }

    public void removeFromCart(Cart cart, Product product) {
        cart.getCartItems().remove(product);
        cartRepository.save(cart);
    }

    public List<Product> getAllProductsInTheCart(Cart cart){
      return   productRepository.findAllProductsByCarts(cart);
    }
}

