package com.ecommerce.store.controller;

import com.ecommerce.store.model.Client;
import com.ecommerce.store.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @GetMapping("/clients")
    public Page<Client> getClients(@RequestParam Map<String, String> filters) {
        return clientService.getClients(filters);
    }

    @PostMapping("/clients")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/clients/{id}")
    public Client deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }

    @DeleteMapping("/clients")
    public void deleteClient(@RequestBody Client client) {
        clientService.deleteClient(client);
    }
}
