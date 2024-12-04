package com.example.pizzeria.models;

import java.util.Arrays;

/**
 * Represents a Meatzza Pizza, which is a specific type of pizza with
 * predefined meat toppings and pricing based on size.
 * This class extends the abstract Pizza class.
 * The Meatzza pizza includes toppings such as sausage, pepperoni, beef, and ham.
 *
 * @author Yousef Naam
 */
public class Meatzza extends Pizza {

    // Constants for the prices of the Meatzza pizza based on size
    private static final double SMALL_PRICE = 17.99;
    private static final double MEDIUM_PRICE = 19.99;
    private static final double LARGE_PRICE = 21.99;

    /**
     * Constructs a Meatzza pizza with the specified crust and size. The Meatzza
     * pizza is initialized with default meat toppings.
     *
     * @param crust
     *            the type of crust for the Meatzza pizza
     * @param size
     *            the size of the Meatzza pizza
     */
    public Meatzza(Crust crust, Size size, String style) {
        super(crust, size, style); // Pass style to the superclass constructor
        // Adds default toppings for Meatzza pizza
        this.getToppings().addAll(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.BEEF, Topping.HAM));
    }

    /**
     * Calculates the price of the Meatzza pizza based on its size.
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