package com.example.pizzeria.models;

/**
 * Interface defining a factory for creating different types of pizzas.
 * Implementing classes are responsible for providing concrete
 * implementations for creating various pizza types, including:
 * - Deluxe
 * - Meatzza
 * - BBQ Chicken
 * - Build Your Own
 *
 * Each method creates a specific type of pizza with predefined or custom attributes.
 *
 * @author Yousef Naam & Lukas Chang
 */
public interface PizzaFactory {

    /**
     * Creates a Deluxe pizza with predefined attributes.
     *
     * @return a new instance of a Deluxe pizza
     */
    Pizza createDeluxe();

    /**
     * Creates a Meatzza pizza with predefined attributes.
     *
     * @return a new instance of a Meatzza pizza
     */
    Pizza createMeatzza();

    /**
     * Creates a BBQ Chicken pizza with predefined attributes.
     *
     * @return a new instance of a BBQ Chicken pizza
     */
    Pizza createBBQChicken();

    /**
     * Creates a "Build Your Own" pizza that can be customized with toppings.
     *
     * @return a new instance of a "Build Your Own" pizza
     */
    Pizza createBuildYourOwn();
}