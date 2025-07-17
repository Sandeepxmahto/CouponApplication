package com.example.loginInfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddCouponDTO {

    private long couponId;
    private List<Long> customerIds;
}
