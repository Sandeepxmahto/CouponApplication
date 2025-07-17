package com.example.loginInfo.service;

import com.example.loginInfo.dto.DiscountCouponInputDTO;
import com.example.loginInfo.dto.ResultDTO;

import java.util.List;

public interface DiscountCouponService {
    ResultDTO createDiscountCoupon(DiscountCouponInputDTO discountCouponInputDTO);

    ResultDTO updateDiscountCouponById(DiscountCouponInputDTO discountCouponInputDTO);

    ResultDTO deleteDiscountCouponById(Long id);

    ResultDTO addCouponToCustomers(Long couponId, List<Long> customerIds);

    ResultDTO applyCoupon(Long customerId, Long couponId, Double amount);

    ResultDTO getAllDiscountCoupons();
}
