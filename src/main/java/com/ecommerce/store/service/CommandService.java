package com.ecommerce.store.service;

import com.ecommerce.store.model.Client;
import com.ecommerce.store.model.Command;
import com.ecommerce.store.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommandService {
    @Autowired
    CommandRepository commandRepository;

    public Command addCommand(Client client){
        Command command=new Command();
        command.setClient(client);
        command.setDate(new Date());
        return commandRepository.save(command);
    }
    public Command addCommand(Command command){
        return commandRepository.save(command);
    }

}
