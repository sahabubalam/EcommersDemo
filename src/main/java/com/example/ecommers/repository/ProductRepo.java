package com.example.ecommers.repository;

import com.example.ecommers.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findAllByCategory_Id(int id);
}
