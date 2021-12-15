package com.example.ecommers.service;


import com.example.ecommers.model.Product;
import com.example.ecommers.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepo pr;
    public void addProduct(Product product)
    {
        pr.save(product);
    }

}
