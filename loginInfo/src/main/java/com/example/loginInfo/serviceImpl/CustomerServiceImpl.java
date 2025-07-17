package com.example.loginInfo.serviceImpl;

import com.example.loginInfo.dto.CreateUserDTO;
import com.example.loginInfo.dto.ResultDTO;
import com.example.loginInfo.entity.Customer;
import com.example.loginInfo.entity.DiscountCoupons;
import com.example.loginInfo.repo.CustomerRepo;
import com.example.loginInfo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer save(Customer customer){
        if (customer != null) {
            return customerRepo.save(customer);
        }
        return null;
    }

    public ResultDTO createUser(CreateUserDTO createUserDTO) {
        if (createUserDTO != null) {
            Customer customer = Customer.builder()
                    .customerName(createUserDTO.getName())
                    .email(createUserDTO.getEmail())
                    .phoneNumber(createUserDTO.getPhone())
                    .build();
            customerRepo.save(customer);
            return new ResultDTO(Boolean.TRUE ,"Customer created successfully", customer);
        }
        return new ResultDTO(Boolean.FALSE,"Invalid input data");
    }

    public Customer getCustomerById(Long id) {
        if (id != null) {
            return customerRepo.findById(id).orElse(null);
        }
        return null;
    }

    public void saveAll(Set<Customer> updatedCustomers) {
        if (updatedCustomers != null && !updatedCustomers.isEmpty()) {
            customerRepo.saveAll(updatedCustomers);
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public List<DiscountCoupons> getAllCouponsByCustomerId(Long customerId) {
        if (customerId != null) {
            return customerRepo.findValidCouponsForCustomer(customerId);
        }
        return new ArrayList<>();
    }

}
