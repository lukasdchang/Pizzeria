package com.example.pizzeria.models;

import java.util.ArrayList;

/**
 * Represents a generic Pizza with customizable toppings, crust, and size.
 * This abstract class serves as a base for specific types of pizzas.
 * Concrete subclasses must implement the abstract price() method to
 * calculate the price based on specific rules.
 *
 * The pizza can have a specified crust, size, and an adjustable list
 * of toppings, with a maximum of 7 allowed toppings.
 *
 * @author Lukas Chang
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings; // List of toppings for the pizza
    private Crust crust;                 // Type of crust for the pizza
    private Size size;                   // Size of the pizza
    private String style;                // Style of pizza
    private static final int MAX_TOPPINGS = 7;

    /**
     * Constructs a Pizza with the specified crust and size.
     * Initializes an empty list of toppings.
     *
     * @param crust the crust type for the pizza
     * @param size  the size of the pizza
     */
    public Pizza(Crust crust, Size size, String style) {
        this.toppings = new ArrayList<>();
        this.crust = crust;
        this.size = size;
        this.style = style;
    }

    /**
     * Retrieves the list of toppings added to the pizza.
     *
     * @return an ArrayList of toppings on the pizza
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Adds a topping to the pizza.
     * A maximum of 7 toppings are allowed; exceeding this limit throws an exception.
     *
     * @param topping the topping to add to the pizza
     * @throws IllegalArgumentException if more than 7 toppings are added
     */
    public void addTopping(Topping topping) {
        if (toppings.size() < MAX_TOPPINGS) {
            toppings.add(topping);
        } else {
            throw new IllegalArgumentException("Maximum of 7 toppings allowed.");
        }
    }

    /**
     * Removes a topping from the pizza, if present.
     *
     * @param topping the topping to be removed from the pizza
     */
    public void removeTopping(Topping topping) {
        toppings.remove(topping);
    }

    /**
     * Retrieves the crust type for the pizza.
     *
     * @return the crust type
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Sets a new size for the pizza.
     *
     * @param size the new size to set for the pizza
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Retrieves the size of the pizza.
     *
     * @return the size of the pizza
     */
    public Size getSize() {
        return size;
    }

    /**
     * Abstract method to calculate the price of the pizza.
     * Must be implemented by subclasses.
     *
     * @return the price of the pizza as a double
     */
    public abstract double price();

    /**
     * Provides a string representation of the pizza, including
     * its toppings, crust type, and size.
     *
     * @return a string representation of the pizza
     */
    @Override
    public String toString() {
        return String.format("%s (%s), %s, %s, $%.2f",
                this.getClass().getSimpleName(), // This will get the name of the class (e.g., "BuildYourOwn", "Deluxe")
                this.getCrust(),
                this.style, // Include the pizza style here
                String.join(", ", getToppings().stream().map(Enum::name).toArray(String[]::new)),
                this.price());
    }
}
