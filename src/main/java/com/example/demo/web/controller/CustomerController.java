package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.entity.Customer;
import com.example.demo.domain.model.customer.*;
import com.example.demo.domain.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseGenericForCustomer saveCustomer(@RequestBody RequestSaveCustomer request) {
        return customerService.saveCustomer(request);
    }

    @PostMapping("/delete")
    public ResponseGenericForCustomer deleteCustomer(@RequestBody RequestDeleteCustomer request) {
        return customerService.deleteCustomer(request);
    }

    @GetMapping("/{customerId}")
    public Customer findById(@PathVariable Long customerId) {
        return customerService.findById(customerId);
    }

    @GetMapping("/identityNo/{identityNo}")
    public Customer findByIdentityNo(@PathVariable Long identityNo) {
        return customerService.findByIdentityNo(identityNo);
    }
}
