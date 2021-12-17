package com.example.ecommers.repository;

import com.example.ecommers.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Integer> {

}
