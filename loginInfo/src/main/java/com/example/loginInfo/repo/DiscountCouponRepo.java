package com.example.loginInfo.repo;

import com.example.loginInfo.entity.DiscountCoupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCouponRepo extends JpaRepository<DiscountCoupons, Long> {
}
