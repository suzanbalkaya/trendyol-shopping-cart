package com.trendyol.shoppingbasket.service.impl;

import com.trendyol.shoppingbasket.model.Campaign;
import com.trendyol.shoppingbasket.model.ShoppingBasket;
import com.trendyol.shoppingbasket.service.CampaignService;

import java.math.BigDecimal;

public class RateCampaignService implements CampaignService {

    private static RateCampaignService instance;

    public static RateCampaignService getInstance() {
        if (instance == null) {
            instance = new RateCampaignService();
        }
        return instance;
    }

    private RateCampaignService() {
    }

    @Override
    public void applyDiscounts(Campaign campaign, ShoppingBasket shoppingBasket) {
        final Integer productSizeForCategory = shoppingBasket.getCategoryAndProductSize().get(campaign.getCategory());

        final Double totalAmount = shoppingBasket.getTotalAmount().doubleValue();
        final Double discountRate = campaign.getDiscountRateOrPrice().doubleValue();

        if (productSizeForCategory > campaign.getProductSize()) {
            shoppingBasket.setTotalDiscount(getTotalDiscountForTotalAmount(totalAmount, discountRate));
        } else {
            shoppingBasket.setTotalDiscount(new BigDecimal(0));
        }
    }

    private BigDecimal getTotalDiscountForTotalAmount(Double totalAmount, Double discountRate) {

        return new BigDecimal(totalAmount - (totalAmount * discountRate / 100));
    }
}
