package com.example.ecommers.controller;

import com.example.ecommers.model.Product;
import com.example.ecommers.model.User;
import com.example.ecommers.model.Wishlist;
import com.example.ecommers.repository.UserRepo;
import com.example.ecommers.repository.WishlistRepo;
import com.example.ecommers.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;

@Controller
public class WishlistController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    WishlistService wishlistService;
    @Autowired
    WishlistRepo wishlistRepo;
    @GetMapping("/user/addToWishlist/{id}")
    public String addToWishlist(@PathVariable int id, Model model, Product product, Principal principal, Wishlist wishlist)
    {
        String name =principal.getName();

        User user  =this.userRepo.getUserByUserName(name);

        wishlist.setDate(new Date());
        wishlist.setUser(user);
        wishlist.setProduct(product);
        System.out.println("user Id "+user.getId());
        wishlistService.saveWishlist(wishlist);

       return "redirect:/";
    }
    @GetMapping("/user/wishlist")
    public String wishList(Model model,Principal principal,Wishlist wishlist)
    {
        String name =principal.getName();

        User user  =this.userRepo.getUserByUserName(name);

        model.addAttribute("wishlists",wishlistService.wishlistByUser(user.getId()));
        return "WishList";
    }
}
