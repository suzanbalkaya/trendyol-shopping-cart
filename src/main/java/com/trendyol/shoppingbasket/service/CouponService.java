package com.trendyol.shoppingbasket.service;

import com.trendyol.shoppingbasket.model.Coupon;
import com.trendyol.shoppingbasket.model.ShoppingBasket;

import java.math.BigDecimal;

public class CouponService {

    private static CouponService instance;

    public static CouponService getInstance() {

        if (instance == null) {
            instance = new CouponService();
        }
        return instance;
    }

    private CouponService() {
    }

    public void applyCoupon(Coupon coupon, ShoppingBasket shoppingBasket) {
        final Double totalAmount = shoppingBasket.getTotalAmount().doubleValue();
        final Double minTotalAmountForCoupon = coupon.getMinTotalAmount().doubleValue();

        if (totalAmount >= minTotalAmountForCoupon) {
            shoppingBasket.setTotalDiscount(getTotalDiscountForTotalAmount(totalAmount, coupon.getDiscountRate().doubleValue()));
        } else {
            shoppingBasket.setTotalDiscount(new BigDecimal(0));
        }
    }

    private BigDecimal getTotalDiscountForTotalAmount(Double totalAmount, Double discountRate) {

        return new BigDecimal(totalAmount - (totalAmount * discountRate / 100));
    }
}
