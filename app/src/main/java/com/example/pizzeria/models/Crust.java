package com.example.pizzeria.models;

/**
 * Enum representing the different crust types available for pizzas.
 * Each crust type corresponds to specific styles or types of pizzas.
 * This can vary between Chicago-style and New York-style pizzas.
 *
 * @author Yousef Naam
 */
public enum Crust {
    DEEP_DISH,        // Chicago-style crust for Deluxe pizza
    PAN,              // Chicago-style crust for BBQ Chicken and Build Your Own
    STUFFED,          // Chicago-style crust for Meatzza
    BROOKLYN,         // New York-style crust for Deluxe pizza
    THIN,             // New York-style crust for BBQ Chicken
    HAND_TOSSED       // New York-style crust for Meatzza and Build Your Own
}
