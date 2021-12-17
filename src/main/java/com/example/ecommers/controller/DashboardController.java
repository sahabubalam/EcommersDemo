package com.example.ecommers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @RequestMapping("/admin")
    public String index()
    {
        return "index";
    }

}
