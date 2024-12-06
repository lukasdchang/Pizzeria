package com.example.pizzeria.models;

import java.util.Arrays;

/**
 * Represents a Meatzza Pizza, a specific type of pizza with predefined meat
 * toppings and pricing based on size.
 *
 * The Meatzza pizza includes toppings such as sausage, pepperoni, beef, and ham.
 * This class extends the abstract Pizza class.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class Meatzza extends Pizza {

    // Constants for the prices of the Meatzza pizza based on size
    private static final double SMALL_PRICE = 17.99;
    private static final double MEDIUM_PRICE = 19.99;
    private static final double LARGE_PRICE = 21.99;

    /**
     * Constructs a Meatzza pizza with the specified crust, size, and style.
     * The Meatzza pizza is initialized with its default meat toppings.
     *
     * @param crust the type of crust for the Meatzza pizza
     * @param size  the size of the Meatzza pizza
     * @param style the style of the pizza (e.g., "New York Style", "Chicago Style")
     */
    public Meatzza(Crust crust, Size size, String style) {
        super(crust, size, style); // Pass crust, size, and style to the superclass constructor
        // Add default toppings for Meatzza pizza
        this.getToppings().addAll(Arrays.asList(
                Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM
        ));
    }

    /**
     * Calculates the price of the Meatzza pizza based on its size.
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