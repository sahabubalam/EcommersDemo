package com.example.ecommers.controller;

import com.example.ecommers.model.Category;
import com.example.ecommers.repository.CategoryRepo;
import com.example.ecommers.repository.ProductRepo;
import com.example.ecommers.service.CategoryService;
import com.example.ecommers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
