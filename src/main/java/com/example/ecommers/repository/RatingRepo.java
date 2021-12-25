package com.example.ecommers.repository;

import com.example.ecommers.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,Integer> {

    List<Rating> getRatingListByProductId(int id);

}
