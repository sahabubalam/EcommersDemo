package com.example.ecommers.repository;

import com.example.ecommers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.email = :email")
     User getUserByUserName(@Param("email") String email);
}
