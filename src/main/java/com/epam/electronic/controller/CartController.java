package com.epam.electronic.controller;

import com.epam.electronic.model.Cart;
import com.epam.electronic.model.Product;
import com.epam.electronic.service.CartService;
import com.epam.electronic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    // Retrieve a cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    // Add a product to the cart
    @PostMapping("/{cartId}/addProduct/{productId}")
    public ResponseEntity<Void> addToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);

        if (cart != null && product != null) {
            cartService.addToCart(cart, product);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Remove a product from the cart
    @PostMapping("/{cartId}/removeProduct/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        Cart cart = cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);

        if (cart != null && product != null) {
            cartService.removeFromCart(cart, product);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Retrieve all products in the cart
    @GetMapping("/{cartId}/products")
    public ResponseEntity<List<Product>> getAllProductsInTheCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
            List<Product> products = cartService.getAllProductsInTheCart(cart);
            return new ResponseEntity<>(products, HttpStatus.OK);
    }
}

