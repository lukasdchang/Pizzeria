package com.example.pizzeria.models;

import java.util.Arrays;

/**
 * Represents a Deluxe Pizza, which is a specific type of pizza with
 * predefined toppings and pricing based on size.
 * This class extends the abstract Pizza class.
 * The Deluxe pizza includes toppings like sausage, pepperoni, green pepper,
 * onion, and mushroom by default.
 *
 * @author Yousef Naam
 *
 */
public class Deluxe extends Pizza {

    // Constants for the prices of the Deluxe pizza based on size
    private static final double SMALL_PRICE = 16.99;
    private static final double MEDIUM_PRICE = 18.99;
    private static final double LARGE_PRICE = 20.99;

    /**
     * Constructs a Deluxe pizza with the specified crust and size. The Deluxe pizza is
     * initialized with default toppings.
     *
     * @param crust
     *            the type of crust for the Deluxe pizza
     * @param size
     *            the size of the Deluxe pizza
     */
    public Deluxe(Crust crust, Size size, String style) {
        super(crust, size, style); // Pass style to the superclass constructor
        // Adds default toppings for Deluxe pizza
        this.getToppings()
                .addAll(Arrays.asList(Topping.SAUSAGE, Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM));
    }

    /**
     * Calculates the price of the Deluxe pizza based on its size.
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