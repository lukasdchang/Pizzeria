package com.example.pizzeria.models;

import java.util.Arrays;

/**
 * Represents a BBQ Chicken Pizza, which is a specific type of pizza with
 * predefined toppings and pricing based on size.
 * This class extends the abstract Pizza class.
 *
 * @author Yousef Naam
 */
public class BBQChicken extends Pizza {

    // Constants for the prices of the BBQ Chicken pizza based on size
    private static final double SMALL_PRICE = 14.99;
    private static final double MEDIUM_PRICE = 16.99;
    private static final double LARGE_PRICE = 19.99;

    /**
     * Constructs a BBQ Chicken pizza with the specified crust and size. The BBQ
     * Chicken pizza is initialized with default toppings.
     *
     * @param crust
     *            the type of crust for the BBQ Chicken pizza
     * @param size
     *            the size of the BBQ Chicken pizza
     */
    public BBQChicken(Crust crust, Size size, String style) {
        super(crust, size, style); // Pass style to the superclass constructor
        // Adds default toppings for BBQ Chicken pizza
        this.getToppings().addAll(
                Arrays.asList(Topping.BBQ_CHICKEN, Topping.GREEN_PEPPER, Topping.PROVOLONE, Topping.CHEDDAR));
    }

    /**
     * Calculates the price of the BBQ Chicken pizza based on its size.
     *
     * @return the price of the pizza as a double
     * @throws IllegalArgumentException
     *             if the pizza size is invalid
     */
    @Override
    public double price() {
        double basePrice;
        switch (getSize()) {
            case SMALL:
                basePrice = SMALL_PRICE;
                break;
            case MEDIUM:
                basePrice = MEDIUM_PRICE;
                break;
            case LARGE:
                basePrice = LARGE_PRICE;
                break;
            default:
                throw new IllegalArgumentException("Invalid size");
        }
        return basePrice;
    }
}