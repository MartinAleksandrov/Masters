package org.example.controllers;

import org.example.steriotypes.Controller;
import org.example.steriotypes.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("")
    public String Index(){
        return "Customer Info";
    }
}
