package com.ecommerce.store.controller;

import com.ecommerce.store.DTO.CommandForm;
import com.ecommerce.store.DTO.CommandProduct;
import com.ecommerce.store.model.Client;
import com.ecommerce.store.model.Command;
import com.ecommerce.store.service.ClientService;
import com.ecommerce.store.service.CommandItemService;
import com.ecommerce.store.service.CommandService;
import com.ecommerce.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CommandService commandService;
    @Autowired
    private CommandItemService commandItemService;
    @PostMapping("/commands")
    public Command saveCommand(@RequestBody CommandForm commandForm){
        Client client=clientService.addClientFromCommand(commandForm);
        Command command=commandService.addCommand(client);
        double total=0;
        for(CommandProduct commandProduct:commandForm.getProducts()){
            commandItemService.creatCommandItem(commandProduct,command);
            total+=productService.getPriceTotal(commandProduct);
        }
        command.setTotalAmount(total);
        return commandService.addCommand(command);
    }

}
