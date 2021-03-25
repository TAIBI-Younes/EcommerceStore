package com.ecommerce.store.DTO;

import com.ecommerce.store.model.Client;
import lombok.Data;

import java.util.List;

@Data
public class CommandForm {
    private Client client;
    private List<CommandProduct> products;
}
