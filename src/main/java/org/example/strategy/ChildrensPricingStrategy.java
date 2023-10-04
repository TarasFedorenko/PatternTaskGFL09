package org.example.strategy;

public class ChildrensPricingStrategy implements MoviePricingStrategy {
    @Override
    public double calculateCost(int rentalDays) {

        double cost = 1.5;
        if (rentalDays > 3) {
            cost += (rentalDays - 3) * 1.5;
        }
        return cost;
    }
}