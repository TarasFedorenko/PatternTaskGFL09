package org.example.utils;

import org.example.enums.MovieType;
import org.example.strategy.ChildrensPricingStrategy;
import org.example.strategy.MoviePricingStrategy;
import org.example.strategy.NewReleasePricingStrategy;
import org.example.strategy.RegularPricingStrategy;

/**
 * There I use a Factory pattern to implements one
 * of pricing strategy so if we need more strategy
 * we can easily add new one
 */
public class MoviePricingStrategyFactory {
    public static MoviePricingStrategy createPricingStrategy(MovieType priceCode) {
        switch (priceCode) {
            case REGULAR:
                return new RegularPricingStrategy();
            case CHILDREN:
                return new ChildrensPricingStrategy();
            case NEW_RELEASE:
                return new NewReleasePricingStrategy();
            default:
                throw new IllegalArgumentException("Unsupported price code: " + priceCode);
        }
    }
}
