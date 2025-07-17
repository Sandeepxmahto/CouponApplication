package com.example.loginInfo.dto;

import com.example.loginInfo.constants.CustomerType;
import com.example.loginInfo.constants.DiscountType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DiscountCouponInputDTO {

    private Long id;
    private String title;
    private String couponCode;
    private DiscountType discountType;
    private Double discountValue;
    private LocalDate expiryDate;
    private CustomerType customerType;
    private Double minimumDiscount;
    private Double maximumDiscount;
    private Integer usagePerCustomer;
}
