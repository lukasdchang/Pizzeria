package com.example.pizzeria.models;

import java.util.Arrays;

/**
 * Represents a BBQ Chicken Pizza, which is a specific type of pizza with
 * predefined toppings and pricing based on size.
 * This class extends the abstract Pizza class.
 */
public class BBQChicken extends Pizza {

    // Constants for the prices of the BBQ Chicken pizza based on size
    private static final double SMALL_PRICE = 14.99;
    private static final double MEDIUM_PRICE = 16.99;
    private static final double LARGE_PRICE = 19.99;

    /**
     * Constructs a BBQ Chicken pizza with the specified crust, size, and style.
     * The BBQ Chicken pizza is initialized with its default toppings.
     *
     * @param crust the type of crust for the BBQ Chicken pizza
     * @param size  the size of the BBQ Chicken pizza
     * @param style the style of the BBQ Chicken pizza (e.g., "New York Style", "Chicago Style")
     */
    public BBQChicken(Crust crust, Size size, String style) {
        super(crust, size, style); // Pass crust, size, and style to the superclass constructor
        // Adds default toppings for BBQ Chicken pizza
        this.getToppings().addAll(Arrays.asList(
                Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR
        ));
    }

    /**
     * Calculates the price of the BBQ Chicken pizza based on its size.
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