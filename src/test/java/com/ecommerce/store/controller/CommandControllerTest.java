package com.ecommerce.store.controller;

import com.ecommerce.store.DTO.CommandForm;
import com.ecommerce.store.DTO.CommandProduct;
import com.ecommerce.store.model.Client;
import com.ecommerce.store.model.Command;
import com.ecommerce.store.model.CommandItem;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.service.ClientService;
import com.ecommerce.store.service.CommandItemService;
import com.ecommerce.store.service.CommandService;
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
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommandControllerTest {

    @InjectMocks
    CommandController commandController;

    @Mock
    private ProductService productService;
    @Mock
    private ClientService clientService;
    @Mock
    private CommandService commandService;
    @Mock
    private CommandItemService commandItemService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders
                .standaloneSetup(commandController)
                .build();
    }

    @Test
    void saveCommand() throws Exception {
        CommandForm commandForm = new CommandForm();

        Client client = new Client();
        client.setId(1L);

        CommandProduct commandProduct_1 = new CommandProduct();
        commandProduct_1.setId(1L);
        commandProduct_1.setQuantity(1);
        CommandProduct commandProduct_2 = new CommandProduct();
        commandProduct_2.setId(2L);
        commandProduct_2.setQuantity(1);

        List<CommandProduct> products = new LinkedList<>();
        products.add(commandProduct_1);
        products.add(commandProduct_2);

        commandForm.setClient(client);
        commandForm.setProducts(products);

        Product product_1 = new Product();
        product_1.setId(1L);
        product_1.setCurrentPrice(100);
        Product product_2 = new Product();
        product_2.setId(2L);
        product_2.setCurrentPrice(200);

        Command command = new Command();
        command.setId(2L);
        command.setClient(client);
        command.setDate(new Date());

        CommandItem commandItem_1 = new CommandItem();
        commandItem_1.setCommand(command);
        commandItem_1.setProduct(product_1);
        commandItem_1.setPrice(product_1.getCurrentPrice());
        commandItem_1.setQuantity(commandProduct_1.getQuantity());
        CommandItem commandItem_2 = new CommandItem();
        commandItem_2.setCommand(command);
        commandItem_2.setProduct(product_2);
        commandItem_2.setPrice(product_2.getCurrentPrice());
        commandItem_2.setQuantity(commandProduct_2.getQuantity());

        when(clientService.addClientFromCommand(commandForm)).thenReturn(client);
        when(commandService.addCommandByClient(client)).thenReturn(command);
        when(commandItemService.creatCommandItem(commandProduct_1, command)).thenReturn(commandItem_1);
        when(commandItemService.creatCommandItem(commandProduct_2, command)).thenReturn(commandItem_2);
        when(productService.getPriceTotal(commandProduct_1)).thenReturn(commandProduct_1.getQuantity() * product_1.getCurrentPrice());
        when(productService.getPriceTotal(commandProduct_2)).thenReturn(commandProduct_2.getQuantity() * product_2.getCurrentPrice());
        when(commandService.addCommandByClient(command)).thenReturn(command);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(commandForm);

        mockMvc.perform(post("/commands")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(jsonPath("$.totalAmount", CoreMatchers.is(300.0)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(clientService, times(1)).addClientFromCommand(commandForm);
        verify(commandService, times(1)).addCommandByClient(client);
        verify(commandItemService, times(1)).creatCommandItem(commandProduct_1, command);
        verify(commandItemService, times(1)).creatCommandItem(commandProduct_2, command);
        verify(productService, times(1)).getPriceTotal(commandProduct_1);
        verify(productService, times(1)).getPriceTotal(commandProduct_2);
        verify(commandService, times(1)).addCommandByClient(command);
    }
}