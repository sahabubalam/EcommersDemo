package com.example.ecommers.controller;

import com.example.ecommers.model.Category;
import com.example.ecommers.repository.CategoryRepo;
import com.example.ecommers.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepo categoryRepo;
    @GetMapping("/")
    public String demo(Model model)
    {
       model.addAttribute("categories",categoryService.getAllCategories());
        return "index2";
    }
}
