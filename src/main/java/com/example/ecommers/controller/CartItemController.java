package com.example.ecommers.controller;

import com.example.ecommers.model.CartItem;
import com.example.ecommers.model.Product;
import com.example.ecommers.model.User;
import com.example.ecommers.repository.CartItemRepo;
import com.example.ecommers.repository.ProductRepo;
import com.example.ecommers.repository.UserRepo;
import com.example.ecommers.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CartItemController {
    @Autowired
    CartItemRepo cartItemRepo;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserRepo userRepo;

    @GetMapping("save/item/{id}")
    public String saveCartItem(@PathVariable int id, CartItem cartItem, Product product, Principal principal)
    {
        try{
            cartItem.setProduct(product);
            String name = principal.getName();
            User user = userRepo.getUserByUserName(name);
            cartItem.setUser(user);
            cartItem.setQuantity(1);
            cartItem.setDate(new Date());
            cartItemService.AddCartItem(cartItem);
            System.out.println("cart data "+cartItem);

        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }
    @GetMapping("/cart/list")
    public String cartList(CartItem cartItem, Model model,Product product,User user)
    {
        List<CartItem> lists =cartItemService.getAllCartItem();
        double sum=0;
        for(int i=0;i<lists.size();i++)
        {
            sum=sum+lists.get(i).getProduct().getPrice();
        }
        model.addAttribute("sum",sum);
        model.addAttribute("cartItems",cartItemService.getAllCartItem());
        return "CartList";
    }
    @GetMapping("/checkout")
    public String checkout()
    {
        return "Checkout";
    }
}
