package org.example.strategy;

/**
 * I used Strategy pattern to separate logic of calculation price for rent
 */
public interface MoviePricingStrategy {
    double calculateCost(int rentalDays);
}
