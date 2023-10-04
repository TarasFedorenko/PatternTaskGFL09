package org.example.strategy;

public class NewReleasePricingStrategy implements MoviePricingStrategy {
    @Override
    public double calculateCost(int rentalDays) {
        return rentalDays * 3.0;
    }
}