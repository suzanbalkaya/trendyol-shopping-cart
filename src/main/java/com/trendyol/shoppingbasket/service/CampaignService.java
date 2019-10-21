package com.trendyol.shoppingbasket.service;

import com.trendyol.shoppingbasket.model.Campaign;
import com.trendyol.shoppingbasket.model.ShoppingBasket;

public interface CampaignService {

    void applyDiscounts(Campaign campaign, ShoppingBasket shoppingBasket);
}
