package com.trendyol.shoppingbasket.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private Category category;
    private BigDecimal price;

    @Override
    public String toString() {
        return "Product{" +
                "name: '" + name + '\'' +
                ", categoryName: " + category +
                ", price: " + price +
                '}';
    }
}
