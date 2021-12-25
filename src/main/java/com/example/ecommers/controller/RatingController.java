package com.example.ecommers.controller;

import com.example.ecommers.model.Category;
import com.example.ecommers.model.Product;
import com.example.ecommers.model.Rating;
import com.example.ecommers.repository.ProductRepo;
import com.example.ecommers.repository.RatingRepo;
import com.example.ecommers.service.ProductService;
import com.example.ecommers.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RatingController {
    @Autowired
    RatingRepo ratingRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;
    @Autowired
    RatingService ratingService;
    @PostMapping("/save/rating")
    public String saveRating(Rating rating,@RequestParam("content") String content, @RequestParam("prating") int prating,@RequestParam("id") int pid)
    {
        rating.setPrating(prating);
        rating.setContent(content);
        rating.setProduct(productRepo.getById(pid));
        ratingRepo.save(rating);
//        rt.setContent(rating.getContent());
//        rt.setRating(rating.getRating());
//        System.out.println("data "+rt);
        return "redirect:/";
    }



}
