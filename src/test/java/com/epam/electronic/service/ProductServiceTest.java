package com.epam.electronic.service;

import com.epam.electronic.model.Product;
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

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product productToCreate = new Product();
        productToCreate.setName("Test Product");
        productToCreate.setPrice(99.99);

        when(productRepository.save(productToCreate)).thenReturn(productToCreate);

        Product createdProduct = productService.createProduct(productToCreate);

        verify(productRepository, times(1)).save(productToCreate);

        // Assertions
        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        assertEquals(99.99, createdProduct.getPrice());
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(99.99);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getProductById(productId);

        verify(productRepository, times(1)).findById(productId);

        assertNotNull(retrievedProduct);
        assertEquals(productId, retrievedProduct.getId());
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals(99.99, retrievedProduct.getPrice());
    }

    @Test
    public void testGetProductByIdNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(productId));
    }

    @Test
    public void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "Product 1", 49.99, 20));
        productList.add(new Product(2L, "Product 2", 29.99, 10));

        when(productRepository.findAll()).thenReturn(productList);

        // Call the service method
        List<Product> products = productService.getAllProducts();

        // Assertions
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    public void testUpdateProduct() {
        Product productToUpdate = new Product();
        productToUpdate.setId(1L);
        productToUpdate.setName("Updated Product");
        productToUpdate.setPrice(79.99);

        when(productRepository.save(productToUpdate)).thenReturn(productToUpdate);

        Product updatedProduct = productService.updateProduct(productToUpdate);

        verify(productRepository, times(1)).save(productToUpdate);

        // Assertions
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(79.99, updatedProduct.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}

