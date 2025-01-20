package org.example.controllers;

import org.example.steriotypes.Controller;
import org.example.steriotypes.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public  String Index(){
            return "Home Content";
    }
}
