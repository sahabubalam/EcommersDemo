package com.example.ecommers.controller;

import com.example.ecommers.message.Message;
import com.example.ecommers.model.User;
import com.example.ecommers.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/admin")
    public String index()
    {
        return "index";
    }
    @GetMapping("/register")
    public String registerForm(Model model)
    {
        model.addAttribute("user",new User());
        return "register";
    }
    @PostMapping("/save/register")
    public String registration(@ModelAttribute  User user, Model model, HttpSession session)
    {
        try {
            user.setEnabled(true);
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            System.out.println("User "+user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Successfully Register","alert-success"));
            return "register";

        }catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("user",user);
            session.setAttribute("message",new Message("Something Went Wrong!!"+e.getMessage(),"alert-danger"));
            return "register";
        }

    }

}
