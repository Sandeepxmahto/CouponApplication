package com.example.loginInfo.controller;

import com.example.loginInfo.dto.CreateUserDTO;
import com.example.loginInfo.dto.ResultDTO;
import com.example.loginInfo.entity.Customer;
import com.example.loginInfo.entity.DiscountCoupons;
import com.example.loginInfo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResultDTO createUser(@RequestBody CreateUserDTO createUserDTO){
        return customerService.createUser(createUserDTO);
    }

    @GetMapping
    public List<Customer> getAllUsers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("coupon/{id}")
    public List<DiscountCoupons> getAllCustomersCoupons(@PathVariable("id") Long customerId) {
        return customerService.getAllCouponsByCustomerId(customerId);
    }
}
