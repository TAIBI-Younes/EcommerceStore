package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandForm;
import com.ecommerce.store.model.Client;
import com.ecommerce.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client getClient(Long id) {
        return clientRepository.findById(id).get();
    }

    public Page<Client> getClients(Map<String, String> filters) {
        return clientRepository.findAll(PageRequest.of(filters.get("page") != null ? Integer.parseInt(filters.get("page")) : 0, filters.get("size") != null ? Integer.parseInt(filters.get("size")) : 10));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client client) {
        Client oldClient = clientRepository.findById(id).get();
        oldClient.equals(client);
        return clientRepository.save(oldClient);
    }

    public Client deleteClient(Long id) {
        Client client = getClient(id);
        clientRepository.deleteById(id);
        return client;
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }
    
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
