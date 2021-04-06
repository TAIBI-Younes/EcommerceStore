package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandProduct;
import com.ecommerce.store.exception.ResourceNotFoundException;
import com.ecommerce.store.model.Command;
import com.ecommerce.store.model.CommandItem;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.repository.CommandItemRepository;
import com.ecommerce.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandItemService {
    @Autowired
    CommandItemRepository commandItemRepository;
    @Autowired
    ProductRepository productRepository;

    public void creatCommandItem(CommandProduct commandProduct, Command command){
        CommandItem commandItem =new CommandItem();
        commandItem.setCommand(command);
        Product product=productRepository.findById(commandProduct.getId()).orElseThrow(() -> new ResourceNotFoundException("product with id " + commandProduct.getId().toString() + " doesn't exist."));
        commandItem.setProduct(product);
        commandItem.setPrice(product.getCurrentPrice());
        commandItem.setQuantity(commandProduct.getQuantity());
        commandItemRepository.save(commandItem);
    }
}
