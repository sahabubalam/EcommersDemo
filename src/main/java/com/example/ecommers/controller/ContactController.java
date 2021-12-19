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
    public static String uploaddir=System.getProperty("user.dir") +"/src/main/resources/static/img";
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
    //@RequestMapping(value = { "/admin/store/contact" }, method = RequestMethod.POST, consumes = {"multipart/form-data"})

    public String storeContact(@ModelAttribute Contact contact,@RequestParam("fileimage") MultipartFile file)
    {

        try{
            //upload file

            if(file.isEmpty())
            {
                System.out.println("nai");
            }
            else
            {
                String imageUUID;
                imageUUID=file.getOriginalFilename();
                Path fileNameAndPath= Paths.get(uploaddir,imageUUID);
                Files.write(fileNameAndPath,file.getBytes());
                contact.setImage(imageUUID);
//                contact.setImage(file.getOriginalFilename());
//                File saveFile= new ClassPathResource("/src/main/resources/static/img").getFile();
//                Path path=   Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("image uploded");
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
        String image=contact.getImage();
        String imagePath=uploaddir+"\\"+image;
        Path path = Paths.get(imagePath);
        if(image!=null)
        {
            try {
                Files.delete(path);
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
        //old contact details
       Contact oldDetails= contactRepo.findById(contact.getId()).get();
        if(!file.isEmpty())
        {
            if(oldDetails.getImage()==null)
            {
                //update new
                String imageUUID;
                imageUUID=file.getOriginalFilename();
                Path fileNameAndPath= Paths.get(uploaddir,imageUUID);
                Files.write(fileNameAndPath,file.getBytes());
                contact.setImage(imageUUID);
                contactService.addContact(contact);
                System.out.println("data "+contact);
            }
            else
            {
                //delete oldphoto
                String image=oldDetails.getImage();
                //find image with path
                String imagePath=uploaddir+"\\"+image;
                Path path = Paths.get(imagePath);
                Files.delete(path);
                //update new
                String imageUUID;
                imageUUID=file.getOriginalFilename();
                Path fileNameAndPath= Paths.get(uploaddir,imageUUID);
                Files.write(fileNameAndPath,file.getBytes());
                contact.setImage(imageUUID);
                contactService.addContact(contact);
                System.out.println("data "+contact);
            }


        }
        else
        {
            if(oldDetails.getImage()==null)
            {
                contactService.addContact(contact);
                System.out.println("data "+contact);
            }
            else
            {
                contact.setImage(oldDetails.getImage());
                contactService.addContact(contact);
                System.out.println("data "+contact);
            }

        }
        System.out.println("data"+contact);
        return "redirect:/admin/contactList";
    }
}
