package com.trendyol.shoppingbasket.service.impl;

import com.trendyol.shoppingbasket.model.Campaign;
import com.trendyol.shoppingbasket.model.ShoppingBasket;
import com.trendyol.shoppingbasket.service.CampaignService;

import java.math.BigDecimal;

public class AmountCampaignService implements CampaignService {

    private static AmountCampaignService instance;

    public static AmountCampaignService getInstance() {
        if (instance == null) {
            instance = new AmountCampaignService();
        }
        return instance;
    }

    private AmountCampaignService() {
    }

    @Override
    public void applyDiscounts(Campaign campaign, ShoppingBasket shoppingBasket) {
        final Integer productSizeForCategory = shoppingBasket.getCategoryAndProductSize().get(campaign.getCategory());

        final Double totalAmount = shoppingBasket.getTotalAmount().doubleValue();
        final Double discountPrice = campaign.getDiscountRateOrPrice().doubleValue();

        if (productSizeForCategory > campaign.getProductSize()) {
            shoppingBasket.setTotalDiscount(getTotalDiscountForTotalAmount(totalAmount, discountPrice));
        } else {
            shoppingBasket.setTotalDiscount(new BigDecimal(0));
        }
    }

    private BigDecimal getTotalDiscountForTotalAmount(Double totalAmount, Double discountPrice) {
        return new BigDecimal(totalAmount - discountPrice);
    }
}
