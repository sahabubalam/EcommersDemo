package com.example.ecommers.controller;

import com.example.ecommers.model.Category;
import com.example.ecommers.repository.CategoryRepo;
import com.example.ecommers.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepo er;

    @GetMapping("/admin/categoryList")

    public String getCategory(Model model)
    {
        model.addAttribute("categories",categoryService.getAllCategories());
        return "CategoryList";
       // return "working";
    }
    @RequestMapping("/admin/createCategory")
    public String category(Model model)
    {
        model.addAttribute("category",new Category());
        return "AddCategory.html";
    }
    @PostMapping("/admin/store/category")
    public String storeCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return "AddCategory.html";
        }
        categoryService.addCategory(category);
        return "redirect:/admin/categoryList";
    }
    @GetMapping("/admin/delete/category/{id}")
    public String deleteCategory(@PathVariable int id)
    {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categoryList";
    }
    @GetMapping("/admin/edit/category/{id}")
    public String editCategory(@PathVariable int id,Model model)
    {
        Optional<Category> category =categoryService.editCategoryById(id);
        model.addAttribute("category",category.get());
        return "EditCategory";

    }
    @PostMapping("/admin/updateCategory/{id}")
    public String updateCategory(@Valid @ModelAttribute Category category ,BindingResult bindingResult,@PathVariable int id)
    {
      // Optional<Category> cate = categoryService.editCategoryById(id);
        if(bindingResult.hasErrors())
        {
            return "EditCategory";
        }
       Category cat = er.getById(id);
        cat.setName(category.getName());
        er.save(cat);
       return "redirect:/admin/categoryList";
    }
    @GetMapping("/admin/update/status/{id}")
    public String updateStatus(@PathVariable int id,Category category)
    {
        Category cat = er.getById(id);
        if(cat.getStatus().equals("1"))
        {
            cat.setStatus("0");
            er.save(cat);
            return "redirect:/admin/categoryList";
        }
        else if(cat.getStatus().equals("0"))
        {
            cat.setStatus("1");
            er.save(cat);
            return "redirect:/admin/categoryList";
        }
        return "redirect:/admin/categoryList";
    }


}
