package com.ecommerce.store.service;

import com.ecommerce.store.model.Client;
import com.ecommerce.store.repository.ClientRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getClient() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("client_1");

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client1));

        Assert.assertEquals(clientService.getClient(1l), client1);
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void getClients() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("client_1");

        Client client2 = new Client();
        client2.setId(2L);
        client2.setName("client_2");

        Map<String, String> filters = new HashMap<>();
        filters.put("size", "1");
        filters.put("page", "0");

        List<Client> listClient = new LinkedList<>();
        listClient.add(client1);
        Page<Client> clientPage = new PageImpl<>(listClient);
        when(clientRepository.findAll(PageRequest.of(Integer.parseInt(filters.get("page")), Integer.parseInt(filters.get("size"))))).thenReturn(clientPage);

        Assert.assertEquals(clientService.getClients(filters).get().findFirst().get(), client1);
        Assert.assertThat(clientService.getClients(filters), is(clientPage));
        Assert.assertThat(clientService.getClients(filters).getTotalElements(), is(1L));
        verify(clientRepository, atLeast(1)).findAll(PageRequest.of(Integer.parseInt(filters.get("page")), Integer.parseInt(filters.get("size"))));
    }

    @Test
    void createClient() {
        Client client1 = new Client();
        client1.setId(1L);
        client1.setName("client_1");

        when(clientRepository.save(client1)).thenReturn(client1);

        Assert.assertEquals(clientService.createClient(client1), client1);
        verify(clientRepository, times(1)).save(client1);

    }

    @Test
    void updateClient() {
        Client oldClient = new Client();
        oldClient.setId(1L);
        oldClient.setName("client_1");

        Client newClient = new Client();
        newClient.setId(1L);
        newClient.setName("client_1");

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(oldClient));
        when(clientRepository.save(oldClient)).thenReturn(oldClient);

        Assert.assertEquals(clientService.updateClient(1L, oldClient), newClient);
        verify(clientRepository, times(1)).save(newClient);
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void deleteClientById() {
        Client client = new Client();
        client.setId(1L);
        client.setName("client_1");

        when(clientRepository.findById(1L)).thenReturn(java.util.Optional.of(client));
        Assert.assertEquals(clientService.deleteClient(1l), client);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void addClientFromCommand() {
    }
}