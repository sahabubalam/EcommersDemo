package com.example.ecommers.service;

import com.example.ecommers.model.User;
import com.example.ecommers.model.Wishlist;
import com.example.ecommers.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepo wishlistRepo;
    public void saveWishlist(Wishlist wishlist)
    {
        wishlistRepo.save(wishlist);
    }
    public List<Wishlist> getWishlistList()
    {
        return wishlistRepo.findAll();
    }
    public List<Wishlist> wishlistByUser(int id)
    {
        return wishlistRepo.getWishlistByUserId(id);
    }

}
