package com.example.ecommers.service;

import com.example.ecommers.model.Rating;
import com.example.ecommers.repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    RatingRepo ratingRepo;
    public List<Rating> getAllRating(int id) {
        return ratingRepo.getRatingListByProductId(id);
    }
}
