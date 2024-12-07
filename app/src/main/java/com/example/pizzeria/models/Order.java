package com.example.pizzeria.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer's order consisting of multiple pizzas.
 * Each order is assigned a unique order number.
 * Provides functionality to add and remove pizzas, calculate the
 * total price, and include sales tax for the order.
 * This class implements Parcelable for use with Android Intents.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class Order implements Parcelable {
    private static int orderCounter = 1; // Static counter to generate unique order numbers
    private static final double SALES_TAX_RATE = 0.06625; // New Jersey's sales tax rate

    private final int orderNumber;       // Unique order number for this instance
    private final List<Pizza> pizzas;    // List of pizzas in the order

    /**
     * Constructs a new Order object with a unique order number
     * and initializes an empty list of pizzas.
     */
    public Order() {
        this.orderNumber = orderCounter++;
        this.pizzas = new ArrayList<>();
    }

    /**
     * Adds a pizza to the order.
     *
     * @param pizza the pizza to add to the order
     * @throws IllegalArgumentException if the provided pizza is null
     */
    public void addPizza(Pizza pizza) {
        if (pizza != null) {
            pizzas.add(pizza);
        } else {
            throw new IllegalArgumentException("Cannot add a null pizza to the order.");
        }
    }

    /**
     * Removes a pizza from the order, if it exists.
     *
     * @param pizza the pizza to remove from the order
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Calculates the total price of all pizzas in the order.
     *
     * @return the total price of the order as a double
     */
    public double calculateTotal() {
        double total = 0.0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total;
    }

    /**
     * Retrieves the unique order number for this order.
     *
     * @return the order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Retrieves a copy of the list of pizzas in the order.
     *
     * @return a list of pizzas in the order
     */
    public List<Pizza> getPizzas() {
        return new ArrayList<>(pizzas); // Return a copy to prevent external modification
    }

    /**
     * Clears all pizzas from the order.
     */
    public void clearOrder() {
        pizzas.clear();
    }

    /**
     * Calculates the total price of the order, including sales tax.
     *
     * @return the total price with tax as a double
     */
    public double calculateTotalWithTax() {
        double subtotal = calculateTotal();
        return subtotal + (subtotal * SALES_TAX_RATE);
    }

    /**
     * Provides a string representation of the order, including order number,
     * list of pizzas, subtotal, and total with tax.
     *
     * @return a string representation of the order details
     */
    @Override
    public String toString() {
        return "Order Number: " + orderNumber + "\n" +
                "Pizzas: " + pizzas + "\n" +
                "Subtotal: $" + String.format("%.2f", calculateTotal()) + "\n" +
                "Total with Tax: $" + String.format("%.2f", calculateTotalWithTax());
    }

    /**
     * Constructs an Order object from a Parcel, restoring its order number and list of pizzas.
     *
     * @param in The Parcel containing the serialized order data.
     */
    protected Order(Parcel in) {
        orderNumber = in.readInt();
        pizzas = in.createTypedArrayList(Pizza.CREATOR);
    }

    /**
     * Writes the Order object's data into a Parcel for serialization.
     *
     * @param dest The Parcel where the data is written.
     * @param flags Flags for how the object should be written (unused in this case).
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderNumber);
        dest.writeTypedList(pizzas);
    }

    /**
     * Describes the contents of the Parcelable object. Typically returns 0.
     *
     * @return An integer representing the content description (always 0).
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable.Creator implementation for creating Order objects from a Parcel
     * or an array of Order objects.
     */
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}