package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandProduct;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getProduct() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("product_1");

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product1));

        Assert.assertEquals(productService.getProduct(1l), product1);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("product_1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("product_2");

        Map<String, String> filters = new HashMap<>();
        filters.put("size", "1");
        filters.put("page", "0");

        List<Product> listProduct = new LinkedList<>();
        listProduct.add(product1);
        Page<Product> productPage = new PageImpl<>(listProduct);
        when(productRepository.findAll(PageRequest.of(Integer.parseInt(filters.get("page")), Integer.parseInt(filters.get("size"))))).thenReturn(productPage);

        Assert.assertEquals(productService.getProducts(filters).get().findFirst().get(), product1);
        assertThat(productService.getProducts(filters), is(productPage));
        assertThat(productService.getProducts(filters).getTotalElements(), is(1L));
        verify(productRepository, atLeast(1)).findAll(PageRequest.of(Integer.parseInt(filters.get("page")), Integer.parseInt(filters.get("size"))));
    }

    @Test
    void createProduct() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("product_1");

        when(productRepository.save(product1)).thenReturn(product1);

        Assert.assertEquals(productService.createProduct(product1), product1);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void updateProduct() {
        Product oldProduct = new Product();
        oldProduct.setId(1L);
        oldProduct.setName("product_1");

        Product newProduct = new Product();
        newProduct.setId(1L);
        newProduct.setName("product_2");

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(oldProduct));
        when(productRepository.save(oldProduct)).thenReturn(oldProduct);

        Assert.assertEquals(productService.updateProduct(1L, newProduct), newProduct);
        verify(productRepository, times(1)).save(oldProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void deleteProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("product_1");

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        Assert.assertEquals(productService.deleteProduct(1l), product);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void getPhoto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("product_1");
        byte[] photo = new byte[12];
        product.setPhoto(photo);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        Assert.assertEquals(productService.getPhoto(1l), photo);

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void uploadPhoto() {
        Product product = new Product();
        product.setId(1L);
        product.setName("product_1");
        byte[] oldPhoto = new byte[12];
        product.setPhoto(oldPhoto);
        byte[] newPhoto = new byte[12];
        Product upDateProduct = new Product();
        upDateProduct.setId(1L);
        upDateProduct.setName("product_1");
        upDateProduct.setPhoto(newPhoto);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(upDateProduct));
        when(productRepository.save(upDateProduct)).thenReturn(upDateProduct);
        Assert.assertEquals(productService.uploadPhoto(newPhoto, 1L), upDateProduct);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getPriceTotal() {
        Product product = new Product();
        product.setId(1L);
        product.setName("product_1");
        product.setCurrentPrice(100);
        CommandProduct commandProduct = new CommandProduct();
        commandProduct.setId(1L);
        commandProduct.setQuantity(1);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Assert.assertThat(productService.getPriceTotal(commandProduct), is(100.0));
    }
}