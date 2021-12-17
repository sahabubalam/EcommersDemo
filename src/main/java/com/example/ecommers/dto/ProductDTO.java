package com.example.ecommers.dto;

import com.example.ecommers.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {

    private int id;
    private int categoryId;
    private String name;
    private double price;
    private String quantity;
    private String description;
    private String status="1";
    private int featured=1;
    private MultipartFile image;
}
