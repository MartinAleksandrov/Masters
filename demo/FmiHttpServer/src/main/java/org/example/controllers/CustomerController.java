package org.example.controllers;

import org.example.steriotypes.*;

@Controller(method = "GET", endpoint = "/customer")
public class CustomerController {

    @GetMapping("/customer")
    public String fetchAllCustomers(){
        return "Customer Info - Get Request";
    }

    @PostMapping("/customer")
    public String createNewCustomer(){
        return "Customer Info - Post Request";
    }

    @PutMapping("/customer")
    public String updateExistingCustomer(){
        return "Customer Info - Put Request";
    }

    @DeleteMapping("/customer")
    public String deleteCustomer(){
        return "Customer Info - Delete Request";
    }
}
