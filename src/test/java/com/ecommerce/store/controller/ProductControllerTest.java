package com.ecommerce.store.controller;

import com.ecommerce.store.model.Product;
import com.ecommerce.store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Autowired
    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    private MockMvc mockMvc;

    private JacksonTester<Product> jsonProduct;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders
                .standaloneSetup(productController)
                .build();
    }


    @Test
    void getPhoto() throws Exception {
        Product product = new Product();
        byte[] photo = new byte[0];
        product.setId(1L);
        product.setPhoto(photo);

        when(productService.getPhoto(1L)).thenReturn(photo);
        MockHttpServletResponse response = mockMvc.perform(get("/photoProduct/1")
                .accept(MediaType.ALL))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEqualTo(photo);
        verify(productService, times(1)).getPhoto(1L);
    }

    @Test
    void uploadPhoto() throws Exception {
        /*
        Product product_1 = new Product();
        byte[] photo = new byte[0];
        product_1.setId(1L);
        Product product_2 = new Product();

        product_2.setId(1L);
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        final MockMultipartFile avatar = new MockMultipartFile("test.png", "test.png", "image/png", inputStream);
        product_2.setPhoto(avatar.getBytes());
        when(productService.uploadPhoto(photo, 1L)).thenReturn(product_2);

        MockHttpServletResponse response = mockMvc.perform(put("/products/uploadPhoto/1")
                .content(avatar.getBytes()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEqualTo(product_2);
        verify(productService, times(1)).uploadPhoto(photo, 1L);
        */
    }


    @Test
    void getProduct() throws Exception{
        Product product = new Product();
        product.setId(1L);

         when(productService.getProduct(1L)).thenReturn(product);
        MockHttpServletResponse response = mockMvc.perform(get("/products/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonProduct.write(product).getJson());
    }

    @Test
    void getProducts() throws Exception{
        Product product_1 = new Product();
        product_1.setId(1L);

        Product product_2 = new Product();
        product_2.setId(2L);

        Map<String, String> filters = new HashMap<>();

        List<Product> products = new ArrayList<>();
        products.add(product_1);
        products.add(product_2);

        Page<Product> productPage = new PageImpl<>(products);

        when(productService.getProducts(filters)).thenReturn(productPage);
        MockHttpServletResponse response = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.content.[0].id", CoreMatchers.is(1)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(productService, times(1)).getProducts(filters);

    }

    @Test
    void createProduct() throws Exception {
        Product product = new Product();
        product.setId(1L);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(product);

        mockMvc.perform(post("/products")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(productService, times(1)).createProduct(product);

    }

    @Test
    void updateProduct() throws Exception{

        Product product = new Product();
        product.setId(1L);
        product.setName("name_1");
        Product upDateProduct = new Product();
        upDateProduct.setId(1L);
        upDateProduct.setName("name_2");

        when(productService.updateProduct(1L, upDateProduct)).thenReturn(upDateProduct);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(upDateProduct);

        mockMvc.perform(put("/products/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(upDateProduct.getName())))
                .andReturn().getResponse().getContentAsString();

        verify(productService, times(1)).updateProduct(1L, upDateProduct);

    }

    @Test
    void deleteProduct() throws Exception{
        Product product = new Product();
        product.setId(1L);

        when(productService.deleteProduct(1L)).thenReturn(product);
        MockHttpServletResponse response = mockMvc.perform(delete("/products/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(productService, times(1)).deleteProduct(1L);
    }
}