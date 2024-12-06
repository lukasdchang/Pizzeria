package com.example.pizzeria.models;

import java.util.Arrays;

/**
 * Represents a Deluxe Pizza, a specific type of pizza with predefined toppings
 * and pricing based on size. The Deluxe pizza includes toppings like sausage,
 * pepperoni, green pepper, onion, and mushroom by default.
 *
 * This class extends the abstract Pizza class.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class Deluxe extends Pizza {

    // Constants for the prices of the Deluxe pizza based on size
    private static final double SMALL_PRICE = 16.99;
    private static final double MEDIUM_PRICE = 18.99;
    private static final double LARGE_PRICE = 20.99;

    /**
     * Constructs a Deluxe pizza with the specified crust, size, and style. The Deluxe
     * pizza is initialized with its default toppings.
     *
     * @param crust the type of crust for the Deluxe pizza
     * @param size  the size of the Deluxe pizza
     * @param style the style of the Deluxe pizza (e.g., "New York Style", "Chicago Style")
     */
    public Deluxe(Crust crust, Size size, String style) {
        super(crust, size, style);
        // Add default toppings for Deluxe pizza
        this.getToppings().addAll(
                Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
    }

    /**
     * Calculates the price of the Deluxe pizza based on its size.
     *
     * @return the price of the pizza as a double
     * @throws IllegalArgumentException if the pizza size is invalid
     */
    @Override
    public double price() {
        switch (getSize()) {
            case SMALL:
                return SMALL_PRICE;
            case MEDIUM:
                return MEDIUM_PRICE;
            case LARGE:
                return LARGE_PRICE;
            default:
                throw new IllegalArgumentException("Invalid size");
        }
    }
}