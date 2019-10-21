package com.trendyol.shoppingbasket.model;

import com.trendyol.shoppingbasket.model.enums.DiscountType;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    private BigDecimal minTotalAmount;
    private BigDecimal discountRate;
    private DiscountType discountType;
}
