package com.example.loginInfo.repo;

import com.example.loginInfo.entity.Customer;
import com.example.loginInfo.entity.DiscountCoupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("""
        SELECT cu.coupon
        FROM CustomerCouponUsage cu
        WHERE cu.customer.id = :customerId
          AND cu.coupon.expiryDate > CURRENT_DATE
          AND (
                cu.coupon.usagePerCustomer IS NULL
                OR cu.coupon.usagePerCustomer = 0
                OR cu.usageCount < cu.coupon.usagePerCustomer
          )
    """)
    List<DiscountCoupons> findValidCouponsForCustomer(@Param("customerId") Long customerId);
}
