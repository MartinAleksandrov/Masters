package com.project02_solar_park_api.solarparkapi.controllers;

import com.project02_solar_park_api.solarparkapi.entities.Customer;
import com.project02_solar_park_api.solarparkapi.http.AppResponse;
import com.project02_solar_park_api.solarparkapi.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService service) {
        this.customerService = service;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer) {

        if(customerService.createCustomer(customer)) {

            return AppResponse.success()
                    .withMessage("Customer created successfully")
                    .build();
        }

        return  AppResponse.error()
                .withMessage("Customer could not be created")
                .build();

    }

    @GetMapping("/customers")
    public  ResponseEntity<?> fetchAllCustomers() {

        ArrayList<Customer> collection = (ArrayList<Customer>) customerService.getAllCustomers();

        return  AppResponse.success()
                .withData(collection)
                .build();

    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getSingleCustomer(@PathVariable int id) {

        Customer customer =  customerService.getSingleCustomer(id);

        if (customer == null) {
            return  AppResponse
                    .error()
                    .withMessage("Customer could not be found or does not exist")
                    .build();
        }

        return  AppResponse
                .success()
                .withDataAsArrray(customer)
                .build();
    }

    @PutMapping("/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {

        boolean isUpdateSuccessful = customerService.updateCustomer(customer);

        if (!isUpdateSuccessful) {
            return  AppResponse
                    .error()
                    .withMessage("Customer data not found")
                    .build();
        }

        return AppResponse
                .success()
                .withMessage("Customer updated successfully")
                .build();
    }

}
