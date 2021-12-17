package com.example.ecommers.service;


import com.example.ecommers.model.Contact;
import com.example.ecommers.repository.CategoryRepo;
import com.example.ecommers.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    ContactRepo contactRepo;
    public List<Contact> getAllContact()
    {
        return contactRepo.findAll();
    }
    public void addContact(Contact contact)
    {
        contactRepo.save(contact);
    }
    public Optional<Contact> getContactById(int id)
    {
        return contactRepo.findById(id);
    }

}
