package com.example.ecommers.controller;

import com.example.ecommers.model.Contact;
import com.example.ecommers.model.Product;
import com.example.ecommers.repository.ContactRepo;
import com.example.ecommers.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ContactController {
    @Autowired
    ContactService contactService;
    @Autowired
    ContactRepo contactRepo;
    @GetMapping("/admin/contact")
    public String createContact(Model model ,Contact contact)
    {
        model.addAttribute("contact",contact);
        return "AddContact";
    }
    @PostMapping("/admin/store/contact")
    public String storeContact(@ModelAttribute Contact contact,@RequestParam("fileimage") MultipartFile file)
    {

        try{
            //upload file

            if(file.isEmpty())
            {
                System.out.println("File not selected");
            }
            else
            {
                contact.setImage(file.getOriginalFilename());
                File saveFile= new ClassPathResource("static/img").getFile();
                Path path=   Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("image uploaded successfully");
            }

            contactService.addContact(contact);
            System.out.println("Data "+contact);


        } catch (Exception e){
            System.out.println("Error"+e.getMessage());
            e.printStackTrace();
        }

        return "AddContact";
    }
    @GetMapping("/admin/contactList")
    public String contactList(Model model)
    {
        model.addAttribute("contacts",contactService.getAllContact());
        return "ContactList";
    }
    @GetMapping("/admin/delete/contact/{id}")
    public String deleteContact(@PathVariable int id)
    {
        Contact contact=contactRepo.findById(id).get();
        if(contact.getImage()!=null)
        {
            try {
                File contactImage= new ClassPathResource("static/img").getFile();
                File imageFile=new File(contactImage,contact.getImage());
                imageFile.delete();
                contactService.deleteContactById(id);
                return "redirect:/admin/contactList";

            } catch (Exception exception) {
                System.out.println("error while uploading image catch:: " + exception.getMessage());
            }
        }
        contactService.deleteContactById(id);
        return "redirect:/admin/contactList";
    }
    @GetMapping("/admin/edit/contact/{id}")
    public String editContact(@PathVariable int id,Model model)
    {
        Contact contact = contactService.getContactById(id).get();
        model.addAttribute("contact",contact);
        return "EditContact";
    }
    @PostMapping("/admin/update/contact")
    public String updateContact(@ModelAttribute Contact contact,@RequestParam("userImage") MultipartFile file,Model model) throws IOException {


       try{
           //old contact details
           Contact oldDetails= contactRepo.findById(contact.getId()).get();

           //image
           if(!file.isEmpty())
           {
               //delete old image
               File deleteOldImage= new ClassPathResource("static/img").getFile();
               File oldFile=new File(deleteOldImage,oldDetails.getImage());
               oldFile.delete();

               //update new image
               File saveFile= new ClassPathResource("static/img").getFile();
               Path path=   Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
               Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
               contact.setImage(file.getOriginalFilename());
           }
           else
           {
               contact.setImage(oldDetails.getImage());
           }
           contactService.addContact(contact);

       }catch(Exception e){

       }
//        System.out.println("data"+contact);
        return "redirect:/admin/contactList";
    }
}
