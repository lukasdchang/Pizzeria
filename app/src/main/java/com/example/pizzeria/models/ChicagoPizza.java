package com.example.pizzeria.models;

/**
 * Represents a factory for creating Chicago-style pizzas.
 * Implements the PizzaFactory interface to provide specific
 * methods for creating different types of pizzas with Chicago-style crusts.
 * By default, pizzas created by this factory use a medium size.
 */
public class ChicagoPizza implements PizzaFactory {

    private static final String STYLE = "Chicago Style";

    /**
     * Creates a Chicago-style Deluxe pizza with a deep-dish crust.
     *
     * @return a new Deluxe pizza with DEEP_DISH crust and MEDIUM size
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.DEEP_DISH, Size.MEDIUM, STYLE);
    }

    /**
     * Creates a Chicago-style BBQ Chicken pizza with a pan crust.
     *
     * @return a new BBQChicken pizza with PAN crust and MEDIUM size
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.PAN, Size.MEDIUM, STYLE);
    }

    /**
     * Creates a Chicago-style Meatzza pizza with a stuffed crust.
     *
     * @return a new Meatzza pizza with STUFFED crust and MEDIUM size
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.STUFFED, Size.MEDIUM, STYLE);
    }

    /**
     * Creates a Chicago-style "Build Your Own" pizza with a pan crust.
     *
     * @return a new BuildYourOwn pizza with PAN crust and MEDIUM size
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.PAN, Size.MEDIUM, STYLE);
    }
}