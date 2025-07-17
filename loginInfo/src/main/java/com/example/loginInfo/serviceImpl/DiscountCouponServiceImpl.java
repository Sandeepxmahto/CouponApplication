package com.example.loginInfo.serviceImpl;

import com.example.loginInfo.constants.CustomerType;
import com.example.loginInfo.constants.DiscountType;
import com.example.loginInfo.dto.DiscountCouponInputDTO;
import com.example.loginInfo.dto.ResultDTO;
import com.example.loginInfo.entity.Customer;
import com.example.loginInfo.entity.CustomerCouponUsage;
import com.example.loginInfo.entity.DiscountCoupons;
import com.example.loginInfo.repo.DiscountCouponRepo;
import com.example.loginInfo.service.CustomerCouponService;
import com.example.loginInfo.service.CustomerService;
import com.example.loginInfo.service.DiscountCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DiscountCouponServiceImpl implements DiscountCouponService {

    @Autowired
    private DiscountCouponRepo discountCouponRepo;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerCouponService customerCouponService;

    private DiscountCoupons save(DiscountCoupons discountCoupons){
        if(discountCoupons != null){
            return discountCouponRepo.save(discountCoupons);
        }
        return null;
    }

    public ResultDTO createDiscountCoupon(DiscountCouponInputDTO discountCouponInputDTO){

        DiscountCoupons.DiscountCouponsBuilder discountCouponsBuilder = DiscountCoupons.builder();

        discountCouponsBuilder.title(discountCouponInputDTO.getTitle());
        discountCouponsBuilder.couponCode(discountCouponInputDTO.getCouponCode());
        discountCouponsBuilder.discountType(discountCouponInputDTO.getDiscountType());
        discountCouponsBuilder.discountValue(discountCouponInputDTO.getDiscountValue());
        discountCouponsBuilder.expiryDate(discountCouponInputDTO.getExpiryDate());
        discountCouponsBuilder.customerType(discountCouponInputDTO.getCustomerType());
        discountCouponsBuilder.minimumDiscount(discountCouponInputDTO.getMinimumDiscount());
        discountCouponsBuilder.maximumDiscount(discountCouponInputDTO.getMaximumDiscount());
        discountCouponsBuilder.usagePerCustomer(discountCouponInputDTO.getUsagePerCustomer());

        DiscountCoupons savedDiscountCoupon = save(discountCouponsBuilder.build());

        if(savedDiscountCoupon.getCustomerType().equals(CustomerType.ALL)){
            customerCouponService.createCouponForAllCustomers(savedDiscountCoupon);
        }

        return new ResultDTO(Boolean.TRUE, "Discount coupon created successfully", savedDiscountCoupon);
    }

    public ResultDTO getAllDiscountCoupons() {
        List<DiscountCoupons> discountCouponsList = discountCouponRepo.findAll();
        return new ResultDTO(Boolean.TRUE, "Discount coupons retrieved successfully", discountCouponsList);
    }

    public ResultDTO updateDiscountCouponById(DiscountCouponInputDTO discountCouponInputDTO){

        if(discountCouponInputDTO.getId() == null){
            return new ResultDTO(Boolean.FALSE, "Invalid Request", null);
        }

        DiscountCoupons discountCoupons = discountCouponRepo.findById(discountCouponInputDTO.getId()).orElse(null);

        if(discountCoupons == null){
            return new ResultDTO(Boolean.FALSE, "Discount coupon not found", null);
        }

        DiscountCoupons.DiscountCouponsBuilder discountCouponsBuilder = discountCoupons.toBuilder();
        discountCouponsBuilder.title(discountCouponInputDTO.getTitle());
        discountCouponsBuilder.couponCode(discountCouponInputDTO.getCouponCode());
        discountCouponsBuilder.discountType(discountCouponInputDTO.getDiscountType());
        discountCouponsBuilder.discountValue(discountCouponInputDTO.getDiscountValue());
        discountCouponsBuilder.expiryDate(discountCouponInputDTO.getExpiryDate());
        discountCouponsBuilder.customerType(discountCouponInputDTO.getCustomerType());
        discountCouponsBuilder.minimumDiscount(discountCouponInputDTO.getMinimumDiscount());
        discountCouponsBuilder.maximumDiscount(discountCouponInputDTO.getMaximumDiscount());
        discountCouponsBuilder.usagePerCustomer(discountCouponInputDTO.getUsagePerCustomer());

        DiscountCoupons updatedDiscountCoupon = save(discountCouponsBuilder.build());

        if(updatedDiscountCoupon.getCustomerType().equals(CustomerType.ALL)){
            customerCouponService.createCouponForAllCustomers(updatedDiscountCoupon);
        }

        return new ResultDTO(Boolean.TRUE, "Discount coupon updated successfully", updatedDiscountCoupon);
    }

    public ResultDTO deleteDiscountCouponById(Long id) {
        if(id == null){
            return new ResultDTO(Boolean.FALSE, "Invalid Request", null);
        }
        DiscountCoupons discountCoupons = discountCouponRepo.findById(id).orElse(null);

        if(discountCoupons == null){
            return new ResultDTO(Boolean.FALSE, "Discount coupon not found", null);
        }
        discountCouponRepo.deleteById(id);
        return new ResultDTO(Boolean.TRUE, "Discount coupon deleted successfully");
    }

    public ResultDTO addCouponToCustomers(Long couponId, List<Long> customerIds) {

        if(couponId == null || customerIds == null || customerIds.isEmpty()) {
            return new ResultDTO(Boolean.FALSE, "Invalid Request", null);
        }

        DiscountCoupons discountCoupons = discountCouponRepo.findById(couponId).orElse(null);

        if(discountCoupons == null) {
            return new ResultDTO(Boolean.FALSE, "Discount coupon not found", null);
        }

        if(discountCoupons.getCustomerType().equals(CustomerType.ALL)){
            return new ResultDTO(Boolean.FALSE, "Coupon is applicable to all customers, no need to add", null);
        }

        Set<Customer> updatedCustomers = new HashSet<>();

        for(Long customerId : customerIds) {
            Customer customer = customerService.getCustomerById(customerId);
            if(customer != null) {
                if(customerCouponService.existByCustomerIdAndCouponId(customerId, couponId)) {
                    CustomerCouponUsage customerCouponUsage = CustomerCouponUsage.builder()
                            .customer(customer)
                            .coupon(discountCoupons)
                            .usageCount(0)
                            .build();
                    customerCouponUsage = customerCouponService.saveCustomerCouponUsage(customerCouponUsage);
                    if(customerCouponUsage != null) {
                        customer.getCouponUsages().add(customerCouponUsage);
                        updatedCustomers.add(customer);
                    }
                }
            } else {
                return new ResultDTO(Boolean.FALSE, "Customer with ID " + customerId + " not found", null);
            }
        }

        if(!updatedCustomers.isEmpty()) {
            customerService.saveAll(updatedCustomers);
        }

        return new ResultDTO(Boolean.TRUE, "Added successfully");
    }

    public ResultDTO applyCoupon(Long customerId, Long couponId, Double amount) {
        if (customerId == null || couponId == null || amount == null || amount <= 0) {
            return new ResultDTO(Boolean.FALSE, "Customer ID and Coupon ID cannot be null", amount);
        }

        CustomerCouponUsage customerCouponUsage = customerCouponService.findByCustomerIdAndCouponId(customerId,couponId);

        if(customerCouponUsage == null) {
            return new ResultDTO(Boolean.FALSE, "Coupon not found for the customer", amount);
        }

        DiscountCoupons coupons = discountCouponRepo.findById(couponId).orElse(null);
        if(coupons == null) {
            return new ResultDTO(Boolean.FALSE, "Coupon not found", amount);
        }

        if(coupons.getExpiryDate().isBefore(LocalDate.now())){
            return new ResultDTO(Boolean.FALSE, "Coupon has expired", amount);
        }

        if(coupons.getUsagePerCustomer() != null && coupons.getUsagePerCustomer() > 0) {
            if(customerCouponUsage.getUsageCount() >= coupons.getUsagePerCustomer()) {
                return new ResultDTO(Boolean.FALSE, "Coupon usage limit reached", amount);
            } else {
                customerCouponUsage.setUsageCount(customerCouponUsage.getUsageCount() + 1);
                customerCouponService.saveCustomerCouponUsage(customerCouponUsage);
            }
        }
        double discountAmount = 0.0;
        if(coupons.getDiscountType().equals(DiscountType.FIXED)){
            discountAmount = coupons.getDiscountValue();
            if(coupons.getMinimumDiscount() != null && amount < coupons.getMinimumDiscount()) {
                return new ResultDTO(Boolean.FALSE, "Amount is less than minimum discount", amount);
            }
            if(coupons.getMaximumDiscount() != null && discountAmount > coupons.getMaximumDiscount()) {
                discountAmount = coupons.getMaximumDiscount();
            }
        } else if(coupons.getDiscountType().equals(DiscountType.PERCENTAGE)){
            discountAmount = (amount * coupons.getDiscountValue()) / 100;
            if(coupons.getMaximumDiscount() != null && discountAmount > coupons.getMaximumDiscount()) {
                discountAmount = coupons.getMaximumDiscount();
            }
        }

        amount -= discountAmount;
        return new ResultDTO(Boolean.TRUE, "Coupon applied successfully", amount);
    }
}
