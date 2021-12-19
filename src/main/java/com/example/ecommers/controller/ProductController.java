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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;


@Controller
public class ProductController {
    public static String uploaddir=System.getProperty("user.dir") +"/src/main/resources/static/productimages";
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepo pr;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("/admin/createProduct")
    public String createProduct(Model model)
    {
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("productDTO",new ProductDTO());
        return "AddProduct";
    }
    @PostMapping("/admin/store/product")
    public String addproduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("proImage") MultipartFile file,@RequestParam("imgName")
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

        return "redirect:/admin/productList";
    }
    @GetMapping("/admin/productList")
    public String getCategory(Model model)
    {
        model.addAttribute("products",productService.getAllProduct());
        return "ProductList";
    }
    @GetMapping("/admin/delete/product/{id}")
    public String deleteProduct(@PathVariable int id)
    {
        //find specific id
        Product product = pr.getById(id);
        //find image by specific id
        String image=product.getImage();
        //find image with path
        String imagePath=uploaddir+"\\"+image;
        Path path = Paths.get(imagePath);

        if(image!=null)
        {
            try {
                Files.delete(path);
                productService.deleteProductById(id);
                return "redirect:/admin/productList";

            } catch (Exception exception) {
                System.out.println("error while uploading image catch:: " + exception.getMessage());
            }
        }
        productService.deleteProductById(id);
        return "redirect:/admin/productList";
    }
    @GetMapping("/admin/edit/product/{id}")
    public String editProduct(@PathVariable int id,Model model)
    {

        Product product = productService.getProductById(id).get();
        model.addAttribute("product",product);
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "EditProduct";
    }
    @PostMapping("/admin/update/product/{id}")
    public String updateProduct(@PathVariable int id,ProductDTO productDTO,Product product,@RequestParam("proImage") MultipartFile file) throws IOException
    {
        System.out.println("data "+product);
        Product pro = productService.getProductById(id).get();
       // ProductDTO productDTO = new ProductDTO();
        System.out.println("data "+pro);


        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryService.editCategoryById(productDTO.getCategoryId()).get());


        if(!file.isEmpty())
        {
            if(pro.getImage()==null)
            {
                String imageUUID;
                imageUUID=file.getOriginalFilename();
                Path fileNameAndPath= Paths.get(uploaddir,imageUUID);
                Files.write(fileNameAndPath,file.getBytes());
                product.setImage(imageUUID);
                productService.addProduct(product);
                System.out.println("data "+product);
            }
            else
            {
                //delete image
                String image = pro.getImage();
                String imagePath=uploaddir+"\\"+image;
                Path path = Paths.get(imagePath);
                Files.delete(path);
                //update image
                String imageUUID;
                imageUUID=file.getOriginalFilename();
                Path fileNameAndPath= Paths.get(uploaddir,imageUUID);
                Files.write(fileNameAndPath,file.getBytes());
                product.setImage(imageUUID);
                productService.addProduct(product);
                System.out.println("data "+product);
            }

        }
        else
        {
            if(pro.getImage()==null)
            {
                productService.addProduct(product);
                System.out.println("data "+product);
            }
            else
            {
                product.setImage(pro.getImage());
                productService.addProduct(product);
                System.out.println("data "+product);
            }
        }
        //pro.setImage(imageUUID);
       // productService.addProduct(product);

        return "redirect:/admin/productList";
    }
}
