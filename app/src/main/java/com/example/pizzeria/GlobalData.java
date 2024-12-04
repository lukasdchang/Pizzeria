package com.example.pizzeria;

import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Pizza;
import com.example.pizzeria.models.Crust;
import com.example.pizzeria.models.Size;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static List<Order> orders = new ArrayList<>();

    // Initialize with dummy data for testing purposes

    static {
        for (int i = 1; i <= 5; i++) {
            Order order = new Order();
            order.addPizza(new Pizza(Crust.THIN, Size.SMALL, "Style " + i) {
                @Override
                public double price() {
                    return 12.99;
                }
            });
            orders.add(order);
        }
    }

    /**
     * Returns the list of orders.
     *
     * @return a list of orders
     */
    public static List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds a new order to the global list.
     *
     * @param order the order to add
     */
    public static void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Removes an order from the global list.
     *
     * @param order the order to remove
     */
    public static void removeOrder(Order order) {
        orders.remove(order);
    }
}