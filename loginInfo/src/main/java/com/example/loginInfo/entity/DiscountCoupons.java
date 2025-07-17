package com.example.loginInfo.entity;

import com.example.loginInfo.constants.CustomerType;
import com.example.loginInfo.constants.DiscountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DiscountCoupons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date lastUpdated;

    private String title;
    private String couponCode;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double discountValue;
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    private Double minimumDiscount;
    private Double maximumDiscount;
    private Integer usagePerCustomer;

    @OneToMany(mappedBy = "coupon")
    @JsonIgnore
    private Set<CustomerCouponUsage> customerUsages = new HashSet<>();
}