package com.trendyol.shoppingbasket.model;

import com.trendyol.shoppingbasket.model.enums.DiscountType;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
    private Category category;
    private BigDecimal discountRateOrPrice;
    private Integer productSize;
    private DiscountType discountType;
}
