package com.example.loginInfo.service;

import com.example.loginInfo.dto.CreateUserDTO;
import com.example.loginInfo.dto.ResultDTO;
import com.example.loginInfo.entity.Customer;
import com.example.loginInfo.entity.DiscountCoupons;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    Customer getCustomerById(Long id);

    void saveAll(Set<Customer> updatedCustomers);

    List<Customer> getAllCustomers();

    List<DiscountCoupons> getAllCouponsByCustomerId(Long customerId);

    ResultDTO createUser(CreateUserDTO createUserDTO);
}
