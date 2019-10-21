package com.trendyol.shoppingbasket.service;

import com.trendyol.shoppingbasket.model.DeliveryCostCalculator;
import com.trendyol.shoppingbasket.model.ShoppingBasket;

import java.math.BigDecimal;

public class DeliveryCostCalculatorService {

    private static DeliveryCostCalculatorService instance;

    public static DeliveryCostCalculatorService getInstance() {

        if (instance == null) {
            instance = new DeliveryCostCalculatorService();
        }
        return instance;
    }

    private DeliveryCostCalculatorService() {
    }

    public void calculate(DeliveryCostCalculator deliveryCostCalculator, ShoppingBasket shoppingBasket) {
        final Double costPerDelivery = deliveryCostCalculator.getCostPerDelivery().doubleValue();
        final Double costPerProduct = deliveryCostCalculator.getCostPerProduct().doubleValue();
        final Double fixedCost = deliveryCostCalculator.getFixedCost().doubleValue();
        final Integer numberOfDeliveries = shoppingBasket.getCategoryAndProductSize().size();
        final Integer numberOfProducts = shoppingBasket.getSize();

        shoppingBasket.setDeliveryCost(getCalculateDeliverCost(costPerDelivery, costPerProduct, fixedCost, numberOfDeliveries, numberOfProducts));
    }

    private BigDecimal getCalculateDeliverCost(Double costPerDelivery, Double costPerProduct, Double fixedCost, Integer numberOfDeliveries, Integer numberOfProducts) {

        return new BigDecimal((costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost);
    }
}
