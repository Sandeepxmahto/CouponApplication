package com.example.loginInfo.repo;

import com.example.loginInfo.entity.CustomerCouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCouponUsageRepo extends JpaRepository<CustomerCouponUsage, Long> {
    boolean existsByCustomerIdAndCouponId(Long customerId, Long couponId);

    CustomerCouponUsage findByCustomerIdAndCouponId(Long customerId, Long couponId);
}
