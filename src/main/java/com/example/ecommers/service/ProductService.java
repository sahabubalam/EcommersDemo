package com.example.ecommers.service;


import com.example.ecommers.model.Product;
import com.example.ecommers.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;
    public List<Product> getAllProduct()
    {
        return productRepo.findAll();
    }
    public void addProduct(Product product)
    {
        productRepo.save(product);
    }
    public void deleteProductById(int id)
    {
        productRepo.deleteById(id);
    }
    public Optional<Product> getProductById(int id)
    {
        return productRepo.findById(id);
    }

}
