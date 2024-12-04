package com.example.pizzeria.models;

import com.example.pizzeria.models.Size;
import com.example.pizzeria.models.Topping;

import java.util.List;

/**
 * Utility class to calculate the price of different types of pizzas based on
 * their type, size, and number of toppings (for "Build Your Own").
 *
 * This class uses predefined base prices for different pizza types and sizes.
 * The pricing for "Build Your Own" pizzas includes a base price plus a cost
 * per topping, while other pizza types have fixed base prices.
 *
 * This class cannot be instantiated and relies solely on the static method
 * `calculatePrice`.
 */
public final class PriceCalculator {

    // Base prices for "Build Your Own" pizza based on size
    private static final double BASE_PRICE_BUILD_YOUR_OWN_SMALL = 8.99;
    private static final double BASE_PRICE_BUILD_YOUR_OWN_MEDIUM = 10.99;
    private static final double BASE_PRICE_BUILD_YOUR_OWN_LARGE = 12.99;
    private static final double TOPPING_PRICE = 1.69; // Price per additional topping

    // Base prices for "Deluxe" pizza based on size
    private static final double BASE_PRICE_DELUXE_SMALL = 16.99;
    private static final double BASE_PRICE_DELUXE_MEDIUM = 18.99;
    private static final double BASE_PRICE_DELUXE_LARGE = 20.99;

    // Base prices for "BBQ Chicken" pizza based on size
    private static final double BASE_PRICE_BBQ_CHICKEN_SMALL = 14.99;
    private static final double BASE_PRICE_BBQ_CHICKEN_MEDIUM = 16.99;
    private static final double BASE_PRICE_BBQ_CHICKEN_LARGE = 19.99;

    // Base prices for "Meatzza" pizza based on size
    private static final double BASE_PRICE_MEATZZA_SMALL = 17.99;
    private static final double BASE_PRICE_MEATZZA_MEDIUM = 19.99;
    private static final double BASE_PRICE_MEATZZA_LARGE = 21.99;

    // Private constructor to prevent instantiation
    private PriceCalculator() {}

    /**
     * Calculates the price of a pizza based on its type, size, and toppings (if applicable).
     *
     * @param pizzaType the type of pizza ("Build Your Own", "Deluxe", "BBQ Chicken", "Meatzza")
     * @param size      the size of the pizza (SMALL, MEDIUM, LARGE)
     * @param toppings  the list of toppings added to the pizza; relevant only for "Build Your Own"
     * @return the calculated price of the pizza rounded to two decimal places
     * @throws IllegalArgumentException if the pizza type is unknown
     */
    public static double calculatePrice(String pizzaType, Size size, List<Topping> toppings) {
        double basePrice;

        switch (pizzaType) {
            case "Build Your Own" -> {
                basePrice = switch (size) {
                    case SMALL -> BASE_PRICE_BUILD_YOUR_OWN_SMALL;
                    case MEDIUM -> BASE_PRICE_BUILD_YOUR_OWN_MEDIUM;
                    case LARGE -> BASE_PRICE_BUILD_YOUR_OWN_LARGE;
                };
                basePrice += toppings.size() * TOPPING_PRICE;
            }
            case "Deluxe" -> basePrice = switch (size) {
                case SMALL -> BASE_PRICE_DELUXE_SMALL;
                case MEDIUM -> BASE_PRICE_DELUXE_MEDIUM;
                case LARGE -> BASE_PRICE_DELUXE_LARGE;
            };
            case "BBQ Chicken" -> basePrice = switch (size) {
                case SMALL -> BASE_PRICE_BBQ_CHICKEN_SMALL;
                case MEDIUM -> BASE_PRICE_BBQ_CHICKEN_MEDIUM;
                case LARGE -> BASE_PRICE_BBQ_CHICKEN_LARGE;
            };
            case "Meatzza" -> basePrice = switch (size) {
                case SMALL -> BASE_PRICE_MEATZZA_SMALL;
                case MEDIUM -> BASE_PRICE_MEATZZA_MEDIUM;
                case LARGE -> BASE_PRICE_MEATZZA_LARGE;
            };
            default -> throw new IllegalArgumentException("Unknown pizza type: " + pizzaType);
        }

        // Round to two decimal places
        return Math.round(basePrice * 100.0) / 100.0;
    }
}