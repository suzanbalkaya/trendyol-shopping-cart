package com.trendyol.shoppingbasket.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingBasket {

    private Map<Product, BigDecimal> productAndTotalProductAmount = new HashMap<>();
    private Map<Category, Integer> categoryAndProductSize = new HashMap<>();
    private BigDecimal totalAmount = new BigDecimal(0);
    private BigDecimal totalDiscount = new BigDecimal(0);
    private BigDecimal deliveryCost = new BigDecimal(0);
    private Integer size = 0;

    @Override
    public String toString() {
        return "{" +
                "productAndTotalProductAmount: {" + productAndTotalProductAmount + "}" +
                ", categoryAndProductSize: {" + categoryAndProductSize + "}" +
                ", totalAmount: " + totalAmount +
                ", totalDiscount: " + totalDiscount +
                ", deliveryCost: " + deliveryCost +
                ", productSize: " + size +
                '}';
    }
}
