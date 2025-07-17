package com.example.loginInfo.dto;

import lombok.Data;

@Data
public class ApplyCouponDTO {
    private Long couponId;
    private Long customerId;
    private Double amount;
}
