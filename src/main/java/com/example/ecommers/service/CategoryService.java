package com.example.ecommers.service;

import com.example.ecommers.model.Category;
import com.example.ecommers.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;
    public List<Category> getAllCategories()
    {
        return categoryRepo.findAll();
    }
    public void addCategory(Category category)
    {
        categoryRepo.save(category);
    }
    public void removeCategoryById(int id)
    {
        categoryRepo.deleteById(id);
    }
    public Optional<Category> editCategoryById(int id)
    {
         return categoryRepo.findById(id);
    }
//    public void updateCategoryById(int id)
//    {
//        categoryRepo.findById(id);
//    }
//    public Optional<Category> getCategoryById(int id)
//    {
//        return categoryRepo.findById(id);
//    }
}
