package com.example.loginInfo.controller;

import com.example.loginInfo.dto.AddCouponDTO;
import com.example.loginInfo.dto.ApplyCouponDTO;
import com.example.loginInfo.dto.DiscountCouponInputDTO;
import com.example.loginInfo.dto.ResultDTO;
import com.example.loginInfo.service.DiscountCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private DiscountCouponService discountCouponService;

    @GetMapping
    public ResultDTO getAllCoupons() {
        return discountCouponService.getAllDiscountCoupons();
    }

    @PostMapping
    public ResultDTO createCoupon(@RequestBody DiscountCouponInputDTO discountCouponInputDTO) {
        return discountCouponService.createDiscountCoupon(discountCouponInputDTO);
    }

    @PostMapping("/update")
    public ResultDTO updateCoupon(@RequestBody DiscountCouponInputDTO discountCouponInputDTO) {
        return discountCouponService.updateDiscountCouponById(discountCouponInputDTO);
    }

    @DeleteMapping("/{id}")
    public ResultDTO deleteCoupon(@PathVariable("id") Long id) {
        return discountCouponService.deleteDiscountCouponById(id);
    }

    @PostMapping("/addToCustomers")
    public ResultDTO addCouponToCustomers(@RequestBody AddCouponDTO addCouponDTO) {
        return discountCouponService.addCouponToCustomers(addCouponDTO.getCouponId(), addCouponDTO.getCustomerIds());
    }

    @PostMapping("/apply")
    public ResultDTO applyCoupon(@RequestBody ApplyCouponDTO applyCouponDTO) {
        return discountCouponService.applyCoupon(applyCouponDTO.getCustomerId(), applyCouponDTO.getCouponId(), applyCouponDTO.getAmount());
    }

}
