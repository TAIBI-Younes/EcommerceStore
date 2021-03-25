package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandForm;
import com.ecommerce.store.model.Client;
import com.ecommerce.store.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

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
