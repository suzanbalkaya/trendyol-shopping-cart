package com.trendyol.shoppingbasket.service;

import com.trendyol.shoppingbasket.model.*;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class ShoppingBasketService {

    private static ShoppingBasketService instance;

    public static ShoppingBasketService getInstance() {

        if (instance == null) {
            instance = new ShoppingBasketService();
        }
        return instance;
    }

    private ShoppingBasketService() {
    }

    public void applyDiscounts(ShoppingBasket shoppingBasket, Campaign campaign) {

        final CampaignReceiver campaignReceiver = CampaignReceiver.getInstance();
        final CampaignService campaignService = campaignReceiver.getReceiverCampaignService(campaign.getDiscountType());

        campaignService.applyDiscounts(campaign, shoppingBasket);
    }

    public void applyCoupon(ShoppingBasket shoppingBasket, Coupon coupon) {

        final CouponService couponService = CouponService.getInstance();

        couponService.applyCoupon(coupon, shoppingBasket);
    }

    public void calculateDeliveryCost(ShoppingBasket shoppingBasket, DeliveryCostCalculator deliveryCostCalculator) {
        final DeliveryCostCalculatorService deliveryCostCalculatorService = DeliveryCostCalculatorService.getInstance();

        deliveryCostCalculatorService.calculate(deliveryCostCalculator, shoppingBasket);
    }

    public void addItem(ShoppingBasket shoppingBasket, Product product, Integer size) {

        Integer basketToCategoryProductSize = shoppingBasket.getCategoryAndProductSize().get(product.getCategory()) == null
                ? 0 : shoppingBasket.getCategoryAndProductSize().get(product.getCategory());

        shoppingBasket.getProductAndTotalProductAmount().put(product, new BigDecimal(product.getPrice().doubleValue() * size));

        shoppingBasket.getCategoryAndProductSize().put(product.getCategory(), basketToCategoryProductSize + size);

        shoppingBasket.setSize(shoppingBasket.getSize() + size);
    }

    public void addOneItem(ShoppingBasket shoppingBasket, Product product) {

        Integer basketToCategoryProductSize = getBasketToCategorySizeForProduct(shoppingBasket, product);

        if (shoppingBasket.getProductAndTotalProductAmount().get(product) == null) {
            shoppingBasket.getProductAndTotalProductAmount().put(product, product.getPrice());
        } else {
            shoppingBasket.getCategoryAndProductSize().put(product.getCategory(), basketToCategoryProductSize + 1);
            shoppingBasket.getProductAndTotalProductAmount().put(product, new BigDecimal(shoppingBasket.getProductAndTotalProductAmount().get(product).doubleValue() + product.getPrice().doubleValue()));
        }

        shoppingBasket.getCategoryAndProductSize().put(product.getCategory(), basketToCategoryProductSize + 1);
        shoppingBasket.setSize(shoppingBasket.getSize() + 1);
    }

    public BigDecimal getBasketTotalAmount(ShoppingBasket shoppingBasket) {

        AtomicReference<BigDecimal> basketAmount = new AtomicReference<>(new BigDecimal(0));

        shoppingBasket.getProductAndTotalProductAmount().forEach(((product, price) -> {
            basketAmount.set(new BigDecimal(basketAmount.get().doubleValue() + price.doubleValue()));
        }));

        return basketAmount.get();
    }

    public String printBasketInfo(ShoppingBasket shoppingBasket) {

        return shoppingBasket.toString();
    }

    private Integer getBasketToCategorySizeForProduct(ShoppingBasket shoppingBasket, Product product) {

        final Integer categorySize = shoppingBasket.getCategoryAndProductSize().get(product.getCategory());
        return categorySize == null ? 0 : categorySize;
    }
}
