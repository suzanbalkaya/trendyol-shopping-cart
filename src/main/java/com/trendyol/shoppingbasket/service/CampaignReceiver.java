package com.trendyol.shoppingbasket.service;

import com.trendyol.shoppingbasket.model.enums.DiscountType;
import com.trendyol.shoppingbasket.service.impl.AmountCampaignService;
import com.trendyol.shoppingbasket.service.impl.RateCampaignService;

import java.util.HashMap;
import java.util.Map;

public class CampaignReceiver {

    private static CampaignReceiver instance;

    private Map<DiscountType, CampaignService> receiverMap;

    public static CampaignReceiver getInstance() {
        if (instance == null) {
            instance = new CampaignReceiver();
        }
        return instance;
    }

    private CampaignReceiver() {
        receiverMap = new HashMap<>();
        this.receiverMap.put(DiscountType.RATE, RateCampaignService.getInstance());
        this.receiverMap.put(DiscountType.AMOUNT, AmountCampaignService.getInstance());
    }

    public CampaignService getReceiverCampaignService(DiscountType discountType) {

        return getReceiverMap().get(discountType);
    }

    private Map<DiscountType, CampaignService> getReceiverMap() {
        return receiverMap;
    }
}
