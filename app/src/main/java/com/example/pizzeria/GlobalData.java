package com.example.pizzeria;

import com.example.pizzeria.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalData is a utility class for managing global application data,
 * such as the current order and the list of placed orders.
 */
public class GlobalData {

    // List to store all placed orders
    private static List<Order> placedOrders = new ArrayList<>();

    // Singleton instance for the current order
    private static Order currentOrder = null;

    /**
     * Gets the current order. If no current order exists, it creates a new one.
     *
     * @return the current order
     */
    public static Order getCurrentOrder() {
        if (currentOrder == null) {
            currentOrder = new Order();
        }
        return currentOrder;
    }

    /**
     * Resets the current order by creating a new one.
     * This should be called after placing an order.
     */
    public static void resetCurrentOrder() {
        currentOrder = new Order();
    }

    /**
     * Adds the current order to the list of placed orders and resets the current order.
     */
    public static void placeCurrentOrder() {
        if (currentOrder != null && !currentOrder.getPizzas().isEmpty()) {
            placedOrders.add(currentOrder);
            resetCurrentOrder();
        }
    }

    /**
     * Gets the list of all placed orders.
     *
     * @return the list of placed orders
     */
    public static List<Order> getPlacedOrders() {
        return placedOrders;
    }

    /**
     * Adds an order to the list of placed orders. Useful for testing or restoring orders.
     *
     * @param order the order to add
     */
    public static void addPlacedOrder(Order order) {
        if (order != null) {
            placedOrders.add(order);
        }
    }

    /**
     * Removes an order from the list of placed orders.
     *
     * @param order the order to remove
     */
    public static void removePlacedOrder(Order order) {
        placedOrders.remove(order);
    }
}