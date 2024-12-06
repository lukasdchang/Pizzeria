package com.example.pizzeria.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Represents a generic Pizza with customizable toppings, crust, and size.
 * This abstract class serves as a base for specific types of pizzas.
 * Concrete subclasses must implement the abstract price() method to
 * calculate the price based on specific rules.
 *
 * The pizza can have a specified crust, size, and an adjustable list
 * of toppings, with a maximum of 7 allowed toppings.
 *
 * Parcelable implementation enables Pizza objects to be passed between
 * Android Activities via Intents or Bundles.
 *
 * @author Yousef Naam & Lukas Chang
 */
public abstract class Pizza implements Parcelable {
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
     * @param style the style of the pizza
     */
    public Pizza(Crust crust, Size size, String style) {
        this.toppings = new ArrayList<>();
        this.crust = crust;
        this.size = size;
        this.style = style;
    }

    // Parcelable requires a no-arg constructor
    protected Pizza() {
        this.toppings = new ArrayList<>();
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

    public String getStyle() {
        return this.style;
    }

    public String getType() {
        return this.getClass().getSimpleName();
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
        try {
            return String.format(
                    Locale.US, // Specify the desired locale
                    "%s (%s), %s %s, Toppings: %s | Price: $%.2f",
                    getType(),                  // Pizza type (e.g., "Deluxe", "Meatzza")
                    getStyle(),                 // Style (e.g., "Chicago Style")
                    getSize(),                  // Size (e.g., "MEDIUM")
                    getCrust(),                 // Crust (e.g., "PAN")
                    getToppings().isEmpty() ? "None" : getToppings().toString(), // Toppings or "None"
                    price()                     // Calculated price
            );
        } catch (Exception e) {
            return "Error displaying pizza details: " + e.getMessage();
        }
    }

    /**
     * Parcelable implementation: Write the object to a Parcel.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(style);
        dest.writeString(crust.name());
        dest.writeString(size.name());
        dest.writeTypedList(toppings);
    }

    /**
     * Parcelable implementation: Read the object from a Parcel.
     */
    protected Pizza(Parcel in) {
        this.style = in.readString();
        this.crust = Crust.valueOf(in.readString());
        this.size = Size.valueOf(in.readString());
        this.toppings = in.createTypedArrayList(Topping.CREATOR);
    }

    /**
     * Parcelable implementation: Describe the contents (typically 0).
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable implementation: CREATOR object for Parcel handling.
     */
    public static final Creator<Pizza> CREATOR = new Creator<Pizza>() {
        @Override
        public Pizza createFromParcel(Parcel in) {
            // Subclasses must handle specific logic
            return null;
        }

        @Override
        public Pizza[] newArray(int size) {
            return new Pizza[size];
        }
    };
}