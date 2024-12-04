package com.example.pizzeria.models;

import main.java.PizzaFactory;

/**
 * Represents a factory for creating New York-style pizzas.
 * Implements the PizzaFactory interface to provide specific
 * methods for creating different types of pizzas with New York-style crusts.
 * By default, pizzas created by this factory use a medium size.
 *
 * This factory supports creating Deluxe, BBQ Chicken, Meatzza,
 * and Build Your Own pizzas with New York-style crusts.
 *
 * - Deluxe uses a BROOKLYN crust.
 * - BBQ Chicken uses a THIN crust.
 * - Meatzza and Build Your Own use a HAND_TOSSED crust.
 *
 * Each method creates an instance of the respective pizza type.
 *
 * @author Lukas Chang
 */
public class NYPizza implements PizzaFactory {

    private static final String STYLE = "NY Style";
    /**
     * Creates a New York-style Deluxe pizza with a Brooklyn crust.
     *
     * @return a new Deluxe pizza with BROOKLYN crust and MEDIUM size
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.BROOKLYN, Size.MEDIUM, STYLE);
    }

    /**
     * Creates a New York-style BBQ Chicken pizza with a thin crust.
     *
     * @return a new BBQChicken pizza with THIN crust and MEDIUM size
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.THIN, Size.MEDIUM, STYLE);
    }

    /**
     * Creates a New York-style Meatzza pizza with a hand-tossed crust.
     *
     * @return a new Meatzza pizza with HAND_TOSSED crust and MEDIUM size
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.HAND_TOSSED, Size.MEDIUM, STYLE);
    }

    /**
     * Creates a New York-style "Build Your Own" pizza with a hand-tossed crust.
     *
     * @return a new BuildYourOwn pizza with HAND_TOSSED crust and MEDIUM size
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.HAND_TOSSED, Size.MEDIUM, STYLE);
    }
}