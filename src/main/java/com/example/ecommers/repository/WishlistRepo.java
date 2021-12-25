package com.example.ecommers.repository;

import com.example.ecommers.model.User;
import com.example.ecommers.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist,Integer> {

    List<Wishlist> getWishlistByUserId(int id);
}
