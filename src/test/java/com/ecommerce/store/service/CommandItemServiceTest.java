package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandProduct;
import com.ecommerce.store.exception.ResourceNotFoundException;
import com.ecommerce.store.model.Command;
import com.ecommerce.store.model.CommandItem;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.repository.CommandItemRepository;
import com.ecommerce.store.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class CommandItemServiceTest {

    @InjectMocks
    CommandItemService commandItemService;

    @Mock
    CommandItemRepository commandItemRepository;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void creatCommandItem() {

        CommandProduct commandProduct = new CommandProduct();
        commandProduct.setId(1L);
        Product product = new Product();
        product.setId(1L);

        Command command = new Command();
        command.setId(1L);
        command.setDate(new Date());
        CommandItem commandItem =new CommandItem();
        commandItem.setCommand(command);
        commandItem.setProduct(product);
        commandItem.setPrice(product.getCurrentPrice());
        commandItem.setQuantity(commandProduct.getQuantity());

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(commandItemRepository.save(commandItem)).thenReturn(commandItem);

        Assert.assertEquals(commandItemService.creatCommandItem(commandProduct, command),commandItem);

        verify(productRepository, times(1)).findById(1L);
        verify(commandItemRepository, times(1)).save(commandItem);
    }
}