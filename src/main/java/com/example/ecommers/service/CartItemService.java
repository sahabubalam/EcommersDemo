package com.example.ecommers.service;

import com.example.ecommers.model.CartItem;
import com.example.ecommers.repository.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    CartItemRepo cartItemRepo;
    public void AddCartItem(CartItem cartItem)
    {
        cartItemRepo.save(cartItem);
    }
    public List<CartItem> getAllCartItem()
    {
        return cartItemRepo.findAll();
    }
}
