package com.example.pizzeria.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the BuildYourOwn pizza model.
 * Validates price calculations, topping limits, and exception handling
 * for maximum toppings and other edge cases.
 *
 * @author
 */
class BuildYourOwnTest {

    /**
     * Tests price calculation after removing a topping from a large pizza.
     * Ensures the price updates correctly when a topping is removed.
     */
    @Test
    void price_UpdatesCorrectly_AfterRemovingTopping() {
        BuildYourOwn largePizza = new BuildYourOwn(Crust.HAND_TOSSED, Size.LARGE, "Build Your Own");
        largePizza.addTopping(Topping.PEPPERONI);
        largePizza.addTopping(Topping.SAUSAGE);
        largePizza.addTopping(Topping.OLIVE);
        largePizza.addTopping(Topping.MUSHROOM);

        // Initial price with 4 toppings
        double initialExpectedPrice = 12.99 + (4 * 1.69);
        assertEquals(initialExpectedPrice, largePizza.price(), 0.01);

        // Remove a topping
        largePizza.removeTopping(Topping.MUSHROOM);

        // Updated price with 3 toppings
        double updatedExpectedPrice = 12.99 + (3 * 1.69);
        assertEquals(updatedExpectedPrice, largePizza.price(), 0.01);
    }

    /**
     * Tests price calculation for a medium pizza with three toppings.
     */
    @Test
    void price_MediumPizza_ThreeToppings() {
        BuildYourOwn mediumPizza = new BuildYourOwn(Crust.PAN, Size.MEDIUM, "Build Your Own");
        mediumPizza.addTopping(Topping.SAUSAGE);
        mediumPizza.addTopping(Topping.OLIVE);
        mediumPizza.addTopping(Topping.MUSHROOM);
        double expectedPrice = 10.99 + (3 * 1.69);
        assertEquals(expectedPrice, mediumPizza.price(), 0.01);
    }

    /**
     * Tests price calculation for a large pizza with the maximum number of toppings (7).
     */
    @Test
    void price_LargePizza_MaxToppings() {
        BuildYourOwn largePizza = new BuildYourOwn(Crust.DEEP_DISH, Size.LARGE, "Build Your Own");
        largePizza.addTopping(Topping.PEPPERONI);
        largePizza.addTopping(Topping.SPINACH);
        largePizza.addTopping(Topping.SAUSAGE);
        largePizza.addTopping(Topping.HAM);
        largePizza.addTopping(Topping.PINEAPPLE);
        largePizza.addTopping(Topping.BBQ_CHICKEN);
        largePizza.addTopping(Topping.OLIVE);
        double expectedPrice = 12.99 + (7 * 1.69);
        assertEquals(expectedPrice, largePizza.price(), 0.01);
    }

    /**
     * Tests price calculation for a medium pizza with no toppings.
     */
    @Test
    void price_MediumPizza_NoToppings() {
        BuildYourOwn mediumPizza = new BuildYourOwn(Crust.THIN, Size.MEDIUM, "Build Your Own");
        double expectedPrice = 10.99;
        assertEquals(expectedPrice, mediumPizza.price(), 0.01);
    }

    /**
     * Tests price calculation for a small pizza with five toppings.
     */
    @Test
    void price_SmallPizza_FiveToppings() {
        BuildYourOwn smallPizza = new BuildYourOwn(Crust.STUFFED, Size.SMALL, "Build Your Own");
        smallPizza.addTopping(Topping.BBQ_CHICKEN);
        smallPizza.addTopping(Topping.HAM);
        smallPizza.addTopping(Topping.MUSHROOM);
        smallPizza.addTopping(Topping.SAUSAGE);
        smallPizza.addTopping(Topping.OLIVE);
        double expectedPrice = 8.99 + (5 * 1.69);
        assertEquals(expectedPrice, smallPizza.price(), 0.01);
    }

    /**
     * Tests that an exception is thrown when adding more than the maximum allowed
     * number of toppings to a BuildYourOwn pizza.
     */
    @Test
    void addTopping_ThrowsExceptionWhenExceedingMaxToppings() {
        BuildYourOwn smallPizza = new BuildYourOwn(Crust.HAND_TOSSED, Size.SMALL, "Build Your Own");
        smallPizza.addTopping(Topping.PEPPERONI);
        smallPizza.addTopping(Topping.SAUSAGE);
        smallPizza.addTopping(Topping.OLIVE);
        smallPizza.addTopping(Topping.MUSHROOM);
        smallPizza.addTopping(Topping.ONION);
        smallPizza.addTopping(Topping.GREEN_PEPPER);
        smallPizza.addTopping(Topping.BBQ_CHICKEN);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                smallPizza.addTopping(Topping.HAM)
        );
        assertEquals("Maximum of 7 toppings allowed.", exception.getMessage());
    }

    /**
     * Tests price calculation for a small pizza with no toppings.
     */
    @Test
    void price_SmallPizza_NoToppings() {
        BuildYourOwn smallPizza = new BuildYourOwn(Crust.HAND_TOSSED, Size.SMALL, "Build Your Own");
        double expectedPrice = 8.99;
        assertEquals(expectedPrice, smallPizza.price(), 0.01);
    }
}