package com.example.ecommers.service;

import com.example.ecommers.model.User;
import com.example.ecommers.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    public void addUser(User user)
    {
        userRepo.save(user);
    }
}
