package com.ecommerce.store.service;

import com.ecommerce.store.DTO.CommandProduct;
import com.ecommerce.store.exception.ResourceNotFoundException;
import com.ecommerce.store.model.Product;
import com.ecommerce.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product with id " + id.toString() + " doesn't exist."));
    }

    public Page<Product> getProducts(Map<String, String> filters) {
        return productRepository.findAll(PageRequest.of(filters.get("page") != null ? Integer.parseInt(filters.get("page")) : 0, filters.get("size") != null ? Integer.parseInt(filters.get("size")) : 10));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product with id " + id.toString() + " doesn't exist."));
        oldProduct.setDescription(product.getDescription());
        oldProduct.setCurrentPrice(product.getCurrentPrice());
        oldProduct.setName(product.getName());
        return productRepository.save(oldProduct);
    }

    public Product deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.deleteById(id);
        return product;
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public void deleteAllProduct() {
        productRepository.deleteAll();
    }

    public byte[] getPhoto(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product with id " + id.toString() + " doesn't exist."));
        return product.getPhoto();
    }

    public void uploadPhoto(byte[] image, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product with id " + id.toString() + " doesn't exist."));
        product.setPhoto(image);
        productRepository.save(product);
    }

    public double getPriceTotal(CommandProduct commandProduct) {
        Product product = productRepository.findById(commandProduct.getId()).orElseThrow(() -> new ResourceNotFoundException("product with id " + commandProduct.getId().toString() + " doesn't exist."));
        return commandProduct.getQuantity() * product.getCurrentPrice();
    }


}
