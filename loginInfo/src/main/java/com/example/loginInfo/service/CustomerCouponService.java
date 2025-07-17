package com.example.loginInfo.service;

import com.example.loginInfo.entity.CustomerCouponUsage;
import com.example.loginInfo.entity.DiscountCoupons;
import org.springframework.stereotype.Service;

@Service
public interface CustomerCouponService {
    CustomerCouponUsage saveCustomerCouponUsage(CustomerCouponUsage customerCouponUsage);

    boolean existByCustomerIdAndCouponId(Long customerId, Long couponId);

    CustomerCouponUsage findByCustomerIdAndCouponId(Long customerId, Long couponId);

    void createCouponForAllCustomers(DiscountCoupons discountCoupon);
}
