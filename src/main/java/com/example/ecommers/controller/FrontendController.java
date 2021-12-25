package com.example.ecommers.controller;

import com.example.ecommers.model.Category;
import com.example.ecommers.model.Product;
import com.example.ecommers.model.Rating;
import com.example.ecommers.repository.CategoryRepo;
import com.example.ecommers.repository.ProductRepo;
import com.example.ecommers.service.CategoryService;
import com.example.ecommers.service.ProductService;
import com.example.ecommers.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class FrontendController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    RatingService ratingService;
    @GetMapping("/")
    public String demo(Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "index2";
    }
    @GetMapping("/categoryBy/product/{id}")
    public String productByCategory(@PathVariable int id,Model model)
    {
        model.addAttribute("products",productService.AllProductByCategoryId(id));
       // System.out.println("data "+productService.AllProductByCategoryId(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        return "ProductByCategory";
    }
    @GetMapping("/product/details/{id}")
    public String productDetails(@PathVariable int id, Model model, Rating rating)
    {
        List<Rating> lists = ratingService.getAllRating(id);
        int sum=0;
        int cnt=0;

        for(int i=0;i<lists.size();i++)
        {
            sum=sum+lists.get(i).getPrating();
            cnt++;
        }
        if(cnt!=0)
        {
            model.addAttribute("lists", sum/cnt);
        }
       else
        {
            model.addAttribute("lists", 0);
        }

        Product product = productRepo.getById(id);
        model.addAttribute("product",product);
        model.addAttribute("rating",rating);
        return "ProductDetails";
    }
}
