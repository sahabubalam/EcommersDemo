package com.example.ecommers.repository;

import com.example.ecommers.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

}
