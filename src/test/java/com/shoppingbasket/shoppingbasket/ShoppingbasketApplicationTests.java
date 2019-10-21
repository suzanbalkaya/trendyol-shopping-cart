package com.shoppingbasket.shoppingbasket;

import com.trendyol.shoppingbasket.model.*;
import com.trendyol.shoppingbasket.model.enums.DiscountType;
import com.trendyol.shoppingbasket.service.ShoppingBasketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class ShoppingbasketApplicationTests {

    private ShoppingBasketService shoppingBasketService;

    private ShoppingBasket shoppingBasket1;
    private ShoppingBasket shoppingBasket2;
    private ShoppingBasket shoppingBasket3;

    private Category food;
    private Category technology;
    private Category car;

    @Before
    public void setUp() {

        shoppingBasketService = ShoppingBasketService.getInstance();
        shoppingBasket1 = new ShoppingBasket();
        shoppingBasket2 = new ShoppingBasket();
        shoppingBasket3 = new ShoppingBasket();

        food = new Category("food");
        technology = new Category("technology");
        car = new Category("car");

        Product apple = new Product("elma", food, new BigDecimal(2));
        Product bmw = new Product("bmw", car, new BigDecimal(150000));
        Product lgPhone = new Product("lg", technology, new BigDecimal(4400));
        Product samsungPhone = new Product("samsung", technology, new BigDecimal(5000));

        shoppingBasketService.addItem(shoppingBasket1, apple, 6);
        shoppingBasketService.addOneItem(shoppingBasket2, lgPhone);
        shoppingBasketService.addOneItem(shoppingBasket3, bmw);
        shoppingBasketService.addItem(shoppingBasket2, samsungPhone, 5);

        shoppingBasket1.setTotalAmount(shoppingBasketService.getBasketTotalAmount(shoppingBasket1));
        shoppingBasket2.setTotalAmount(shoppingBasketService.getBasketTotalAmount(shoppingBasket2));
        shoppingBasket3.setTotalAmount(shoppingBasketService.getBasketTotalAmount(shoppingBasket3));
    }

    @Test
    public void shouldBasket1ProductSizeQuery() {

        assertEquals(shoppingBasket1.getSize(), Optional.of(6).get());
    }

    @Test
    public void shouldBasket2ProductSizeQuery() {

        assertEquals(shoppingBasket2.getSize(), Optional.of(6).get());
    }

    @Test
    public void shouldSuccessfullyBasket2ApplyCampaignToDiscountRateForCategory() {

        BigDecimal basketCampaignDiscountRate = new BigDecimal(20);
        BigDecimal beforeBasket2TotalDiscountAmount = shoppingBasket2.getTotalDiscount();
        Campaign campaign = new Campaign(technology, basketCampaignDiscountRate, 2, DiscountType.RATE);

        log.info("before basket2 totalAmount : {} and discountAmount: {}", shoppingBasket2.getTotalAmount().toString(), shoppingBasket2.getTotalDiscount().toString());
        shoppingBasketService.applyDiscounts(shoppingBasket2, campaign);
        log.info("after basket2 totalAmount : {} and discountAmount: {}", shoppingBasket2.getTotalAmount().toString(), shoppingBasket2.getTotalDiscount().toString());

        assertTrue(beforeBasket2TotalDiscountAmount.doubleValue() < shoppingBasket2.getTotalDiscount().doubleValue());
    }

    @Test
    public void shouldSuccessfullyBasket1ApplyCampaignToDiscountAmountForCategory() {

        BigDecimal basketCampaignDiscountAmount = new BigDecimal(5);
        Campaign campaign = new Campaign(food, basketCampaignDiscountAmount, 5, DiscountType.AMOUNT);
        BigDecimal beforeBasket1TotalDiscountAmount = shoppingBasket1.getTotalDiscount();

        log.info("before basket1 totalAmount : {} and discountAmount: {}", shoppingBasket1.getTotalAmount().toString(), shoppingBasket1.getTotalDiscount().toString());
        shoppingBasketService.applyDiscounts(shoppingBasket1, campaign);
        log.info("after basket1 totalAmount : {} and discountAmount: {}", shoppingBasket1.getTotalAmount().toString(), shoppingBasket1.getTotalDiscount().toString());

        assertTrue(beforeBasket1TotalDiscountAmount.doubleValue() < shoppingBasket1.getTotalDiscount().doubleValue());
    }

    @Test
    public void shouldSuccessfullyBasket3ApplyCampaignToCouponRateForCategory() {

        BigDecimal basketCampaignDiscountRate = new BigDecimal(10);
        BigDecimal basketMinAmountForCoupon = new BigDecimal(10000);
        Coupon coupon = new Coupon(basketMinAmountForCoupon, basketCampaignDiscountRate, DiscountType.RATE);

        log.info("before basket3 totalAmount : {} and discountAmount: {}", shoppingBasket3.getTotalAmount().toString(), shoppingBasket3.getTotalDiscount().toString());
        shoppingBasketService.applyCoupon(shoppingBasket3, coupon);
        log.info("after basket3 totalAmount : {} and discountAmount: {}", shoppingBasket3.getTotalAmount().toString(), shoppingBasket3.getTotalDiscount().toString());

        assertTrue(basketCampaignDiscountRate.doubleValue() < shoppingBasket3.getTotalDiscount().doubleValue());
    }

    @Test
    public void shouldSuccessfullyBasketsCalculateDeliveryCost() {

        BigDecimal costPerDelivery = new BigDecimal(2);
        BigDecimal costPerProduct = new BigDecimal(3);
        BigDecimal fixedCost = new BigDecimal(2.99);

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);

        shoppingBasketService.calculateDeliveryCost(shoppingBasket3, deliveryCostCalculator);
        shoppingBasketService.calculateDeliveryCost(shoppingBasket2, deliveryCostCalculator);
        shoppingBasketService.calculateDeliveryCost(shoppingBasket1, deliveryCostCalculator);

        assertTrue(shoppingBasket3.getDeliveryCost().doubleValue() > 0);
        assertTrue(shoppingBasket2.getDeliveryCost().doubleValue() > 0);
        assertTrue(shoppingBasket1.getDeliveryCost().doubleValue() > 0);
    }

    @Test
    public void shouldBasketInfo() {

        BigDecimal basketCampaignDiscountRate = new BigDecimal(20);
        Campaign campaignBasket2 = new Campaign(technology, basketCampaignDiscountRate, 2, DiscountType.RATE);
        shoppingBasketService.applyDiscounts(shoppingBasket2, campaignBasket2);

        BigDecimal basketCampaignDiscountAmount = new BigDecimal(5);
        Campaign campaignBasket1 = new Campaign(food, basketCampaignDiscountAmount, 2, DiscountType.AMOUNT);
        shoppingBasketService.applyDiscounts(shoppingBasket1, campaignBasket1);

        BigDecimal basketCampaignDiscountRateForCoupon = new BigDecimal(10);
        BigDecimal basketMinAmountForCoupon = new BigDecimal(10000);
        Coupon coupon = new Coupon(basketMinAmountForCoupon, basketCampaignDiscountRateForCoupon, DiscountType.RATE);
        shoppingBasketService.applyCoupon(shoppingBasket3, coupon);

        BigDecimal costPerDelivery = new BigDecimal(2);
        BigDecimal costPerProduct = new BigDecimal(3);
        BigDecimal fixedCost = new BigDecimal(2.99);

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);

        shoppingBasketService.calculateDeliveryCost(shoppingBasket3, deliveryCostCalculator);
        shoppingBasketService.calculateDeliveryCost(shoppingBasket2, deliveryCostCalculator);
        shoppingBasketService.calculateDeliveryCost(shoppingBasket1, deliveryCostCalculator);

        String basket1Info = shoppingBasketService.printBasketInfo(shoppingBasket1);
        String basket2Info = shoppingBasketService.printBasketInfo(shoppingBasket2);
        String basket3Info = shoppingBasketService.printBasketInfo(shoppingBasket3);

        System.out.println(basket1Info);
        System.out.println(basket2Info);
        System.out.println(basket3Info);

        assertNotNull(basket1Info);
        assertNotNull(basket2Info);
        assertNotNull(basket3Info);
    }
}
