package com.example.ecommers.controller;

import com.example.ecommers.dto.ProductDTO;
import com.example.ecommers.model.Product;
import com.example.ecommers.repository.CategoryRepo;
import com.example.ecommers.repository.ProductRepo;
import com.example.ecommers.service.CategoryService;
import com.example.ecommers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Controller
public class ProductController {
    public static String uploaddir=System.getProperty("user.dir") +"/src/main/resources/static/productimages";
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepo pr;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("/createProduct")
    public String createProduct(Model model)
    {
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("productDTO",new ProductDTO());
        return "AddProduct";
    }
    @PostMapping("/store/product")
    public String addproduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("image") MultipartFile file,@RequestParam("imgName")
                                         String imgName) throws IOException
    {
        Product product=new Product();
//        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.editCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());

        String imageUUID;
        if(!file.isEmpty())
        {
            imageUUID=file.getOriginalFilename();
            Path fileNameAndPath= Paths.get(uploaddir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        } else{
            imageUUID=imgName;
        }
        product.setImage(imageUUID);
        productService.addProduct(product);

        return "redirect:/categoryList";
    }
}
