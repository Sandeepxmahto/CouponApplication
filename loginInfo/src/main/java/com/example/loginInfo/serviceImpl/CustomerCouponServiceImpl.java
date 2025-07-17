package com.example.loginInfo.serviceImpl;

import com.example.loginInfo.entity.Customer;
import com.example.loginInfo.entity.CustomerCouponUsage;
import com.example.loginInfo.entity.DiscountCoupons;
import com.example.loginInfo.repo.CustomerCouponUsageRepo;
import com.example.loginInfo.service.CustomerCouponService;
import com.example.loginInfo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCouponServiceImpl implements CustomerCouponService {

    @Autowired
    private CustomerCouponUsageRepo customerCouponUsageRepo;

    @Autowired
    private CustomerService customerService;

    public CustomerCouponUsage saveCustomerCouponUsage(CustomerCouponUsage customerCouponUsage) {
        if (customerCouponUsage != null) {
            customerCouponUsageRepo.save(customerCouponUsage);
        }
        return null;
    }

    public boolean existByCustomerIdAndCouponId(Long customerId, Long couponId) {
        return customerCouponUsageRepo.existsByCustomerIdAndCouponId(customerId,couponId);
    }

    public CustomerCouponUsage findByCustomerIdAndCouponId(Long customerId, Long couponId) {
        return customerCouponUsageRepo.findByCustomerIdAndCouponId(customerId,couponId);
    }

    public void createCouponForAllCustomers(DiscountCoupons discountCoupon) {
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            if (!existByCustomerIdAndCouponId(customer.getId(), discountCoupon.getId())) {
                CustomerCouponUsage customerCouponUsage = CustomerCouponUsage.builder()
                        .customer(customer)
                        .coupon(discountCoupon)
                        .usageCount(0)
                        .build();
                saveCustomerCouponUsage(customerCouponUsage);
            }
        }
    }

}
