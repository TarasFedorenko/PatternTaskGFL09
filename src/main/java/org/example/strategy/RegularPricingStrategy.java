package org.example.strategy;

public class RegularPricingStrategy implements MoviePricingStrategy {
    @Override
    public double calculateCost(int rentalDays) {

        double cost = 2.0;
        if (rentalDays > 2) {
            cost += (rentalDays - 2) * 1.5;
        }
        return cost;
    }
}

