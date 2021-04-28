package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandForm;
import com.ecommerce.store.exception.ResourceNotFoundException;
import com.ecommerce.store.model.Client;
import com.ecommerce.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("client with id " + id.toString() + " doesn't exist."));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Page<Client> getClients(Map<String, String> filters) {
        return clientRepository.findAll(PageRequest.of(filters.get("page") != null ? Integer.parseInt(filters.get("page")) : 0, filters.get("size") != null ? Integer.parseInt(filters.get("size")) : 10));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Client updateClient(Long id, Client client) {
        Client oldClient = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("client with id " + id.toString() + " doesn't exist."));
        oldClient.equals(client);
        return clientRepository.save(oldClient);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Client deleteClient(Long id) {
        Client client = getClient(id);
        clientRepository.deleteById(id);
        return client;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Client addClientFromCommand(CommandForm commandForm){
        Client client=new Client();
        client.setName(commandForm.getClient().getName());
        client.setEmail(commandForm.getClient().getEmail());
        client.setAddress(commandForm.getClient().getAddress());
        client.setPhoneNumber(commandForm.getClient().getPhoneNumber());
        client.setUsername(commandForm.getClient().getUsername());
        return clientRepository.save(client);
    }
}
