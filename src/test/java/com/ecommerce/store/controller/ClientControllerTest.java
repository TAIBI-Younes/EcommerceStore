package com.ecommerce.store.controller;

import com.ecommerce.store.model.Client;
import com.ecommerce.store.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
class ClientControllerTest {

    @Autowired
    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private MockMvc mockMvc;

    private JacksonTester<Client> jsonClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .build();
    }

    @Test
    void getClient() throws Exception {
        Client client = new Client();
        client.setId(1L);

        when(clientService.getClient(1L)).thenReturn(client);

        MockHttpServletResponse response = mockMvc.perform(get("/clients/1")
                .accept(MediaType.ALL))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonClient.write(client).getJson());

    }

    @Test
    void getClients() throws Exception {
        Client client_1 = new Client();
        client_1.setId(1L);

        Client client_2 = new Client();
        client_2.setId(2L);

        Map<String, String> filters = new HashMap<>();

        List<Client> clients = new ArrayList<>();
        clients.add(client_1);
        clients.add(client_2);

        Page<Client> clientPage = new PageImpl<>(clients);

        when(clientService.getClients(filters)).thenReturn(clientPage);
        MockHttpServletResponse response = mockMvc.perform(get("/clients")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.content.[0].id", CoreMatchers.is(1)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(clientService, times(1)).getClients(filters);

    }

    @Test
    void createClient() throws Exception {
        Client client = new Client();
        client.setId(1L);

        when(clientService.createClient(any(Client.class))).thenReturn(client);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(client);

        mockMvc.perform(post("/clients")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(clientService, times(1)).createClient(client);

    }

    @Test
    void updateClient() throws Exception {

        Client client = new Client();
        client.setId(1L);
        client.setName("name_1");
        Client upDateClient = new Client();
        upDateClient.setId(1L);
        upDateClient.setName("name_2");

        when(clientService.updateClient(1L, upDateClient)).thenReturn(upDateClient);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(upDateClient);

        mockMvc.perform(put("/clients/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(upDateClient.getName())))
                .andReturn().getResponse().getContentAsString();

        verify(clientService, times(1)).updateClient(1L, upDateClient);

    }

    @Test
    void deleteClient() throws Exception {
        Client client = new Client();
        client.setId(1L);

        when(clientService.deleteClient(1L)).thenReturn(client);
        MockHttpServletResponse response = mockMvc.perform(delete("/clients/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(clientService, times(1)).deleteClient(1L);
    }

}