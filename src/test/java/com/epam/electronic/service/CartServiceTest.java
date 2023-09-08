package com.epam.electronic.service;

import com.epam.electronic.model.Cart;
import com.epam.electronic.model.Product;
import com.epam.electronic.repository.CartRepository;
import com.epam.electronic.repository.ProductRepository;
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

public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCart() {
        Cart cartToCreate = new Cart();

        when(cartRepository.save(cartToCreate)).thenReturn(cartToCreate);

        Cart createdCart = cartService.createCart(cartToCreate);

        verify(cartRepository, times(1)).save(cartToCreate);

        assertNotNull(createdCart);
    }

    @Test
    public void testGetCartById() {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        Cart retrievedCart = cartService.getCartById(cartId);

        verify(cartRepository, times(1)).findById(cartId);

        assertNotNull(retrievedCart);
        assertEquals(cartId, retrievedCart.getId());
    }

    @Test
    public void testGetCartByIdNotFound() {
        Long cartId = 1L;

        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartService.getCartById(cartId));
    }

    @Test
    public void testAddToCart() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setId(1L);

        cartService.addToCart(cart, product);

        verify(cartRepository, times(1)).save(cart);

        assertTrue(cart.getCartItems().contains(product));
    }

    @Test
    public void testRemoveFromCart() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setId(1L);

        cart.getCartItems().add(product);

        cartService.removeFromCart(cart, product);

        verify(cartRepository, times(1)).save(cart);

        assertFalse(cart.getCartItems().contains(product));
    }

    @Test
    public void testGetAllProductsInTheCart() {
        Cart cart = new Cart();
        List<Product> productsInCart = new ArrayList<>();
        productsInCart.add(new Product(1L, "Product 1", 49.99, 20));
        productsInCart.add(new Product(2L, "Product 2", 29.99, 10));

        when(productRepository.findAllProductsByCarts(cart)).thenReturn(productsInCart);

        List<Product> retrievedProducts = cartService.getAllProductsInTheCart(cart);

        verify(productRepository, times(1)).findAllProductsByCarts(cart);

        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
    }
}

