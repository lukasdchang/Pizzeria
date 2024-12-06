package com.example.pizzeria.models;

import java.util.Locale;

/**
 * Represents a customizable "Build Your Own" pizza that allows customers
 * to select their desired toppings. The price is determined based on the
 * size of the pizza and the number of toppings.
 * This class extends the abstract Pizza class.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class BuildYourOwn extends Pizza {

    // Base prices for Build Your Own pizza based on size
    private static final double BASE_PRICE_SMALL = 8.99;
    private static final double BASE_PRICE_MEDIUM = 10.99;
    private static final double BASE_PRICE_LARGE = 12.99;
    private static final double TOPPING_PRICE = 1.69; // Cost per topping
    private static final int MAX_TOPPINGS = 7; // Maximum number of allowed toppings

    /**
     * Constructs a "Build Your Own" pizza with the specified crust, size, and style.
     *
     * @param crust the type of crust for the pizza
     * @param size  the size of the pizza
     * @param style the style of the pizza (e.g., "New York Style", "Chicago Style")
     */
    public BuildYourOwn(Crust crust, Size size, String style) {
        super(crust, size, style);
    }

    /**
     * Adds a topping to the pizza, provided it does not exceed the maximum
     * number of allowed toppings.
     *
     * @param topping the topping to add to the pizza
     * @throws IllegalArgumentException if adding the topping exceeds the maximum limit
     */
    @Override
    public void addTopping(Topping topping) {
        if (getToppings().size() < MAX_TOPPINGS) {
            super.addTopping(topping);
        } else {
            throw new IllegalArgumentException("Maximum of " + MAX_TOPPINGS + " toppings allowed.");
        }
    }

    public String getType() {
        return "Build Your Own";
    }

    /**
     * Calculates the price of the "Build Your Own" pizza based on its size
     * and the number of toppings added.
     *
     * @return the price of the pizza as a double
     */
    @Override
    public double price() {
        double basePrice;
        switch (getSize()) {
            case SMALL:
                basePrice = BASE_PRICE_SMALL;
                break;
            case MEDIUM:
                basePrice = BASE_PRICE_MEDIUM;
                break;
            case LARGE:
                basePrice = BASE_PRICE_LARGE;
                break;
            default:
                throw new IllegalArgumentException("Invalid size");
        }
        // Calculate the total price based on the base price and number of toppings
        return basePrice + (getToppings().size() * TOPPING_PRICE);
    }

    /**
     * Returns a string representation of the "Build Your Own" pizza, including
     * the size, crust, style, toppings, and calculated price.
     *
     * @return a string representation of the pizza
     */
    @Override
    public String toString() {
        try {
            return String.format(
                    Locale.US, // Specify the desired locale
                    "%s (%s), %s %s, Toppings: %s | Price: $%.2f",
                    getType(),                  // Pizza type (e.g., "Build Your Own")
                    getStyle(),                 // Style (e.g., "Chicago")
                    getSize(),                  // Size (e.g., "MEDIUM")
                    getCrust(),                 // Crust (e.g., "PAN")
                    getToppings().isEmpty() ? "None" : getToppings().toString(), // Toppings or "None"
                    price()                     // Calculated price
            );
        } catch (Exception e) {
            return "Error displaying pizza details: " + e.getMessage();
        }
    }
}