package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.adapters.PizzaAdapter;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Pizza;

import java.util.ArrayList;

/**
 * OrderActivity handles the user's current order. It provides functionality
 * for viewing the order, removing pizzas, clearing the order, and placing the
 * order. The activity dynamically updates the order details including subtotal,
 * sales tax, and total amount.
 *
 * UI Components:
 * - RecyclerView for displaying pizzas in the order
 * - Buttons for removing a pizza, clearing the order, and placing the order
 * - TextViews for showing order details (order number, subtotal, sales tax, total)
 *
 * This activity integrates with the GlobalData class to manage the current order.
 *
 * Sales Tax: 6.625% (New Jersey sales tax).
 *
 * @author Yousef Naam and Lukas Chang
 */
public class OrderActivity extends AppCompatActivity {

    private static final double SALES_TAX_RATE = 0.06625; // New Jersey sales tax rate

    // UI components
    private TextView orderNumberLabel;
    private RecyclerView orderRecyclerView;
    private TextView subtotalLabel, salesTaxLabel, orderTotalLabel;
    private Button removePizzaButton, clearOrderButton, placeOrderButton;

    private PizzaAdapter pizzaAdapter; // Adapter for RecyclerView
    private Order currentOrder; // Current order object

    /**
     * Called when the activity is first created. Sets up the UI components,
     * initializes the RecyclerView, and configures button listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this contains the data
     *                           it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Initialize UI components
        initializeUIComponents();

        // Enable the back button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the current order from GlobalData
        currentOrder = GlobalData.getCurrentOrder();

        // Set up RecyclerView and other components
        setupRecyclerView();
        updateOrderNumber();
        updateTotals();
        setupButtonListeners();
    }

    /**
     * Handles the action bar's back button click to navigate to the main menu.
     *
     * @param item the selected menu item
     * @return true if the home action is handled; otherwise, false
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the main menu
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the UI components by linking them to their XML counterparts.
     */
    private void initializeUIComponents() {
        orderNumberLabel = findViewById(R.id.orderNumberLabel);
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        subtotalLabel = findViewById(R.id.subtotalLabel);
        salesTaxLabel = findViewById(R.id.salesTaxLabel);
        orderTotalLabel = findViewById(R.id.orderTotalLabel);
        removePizzaButton = findViewById(R.id.removePizzaButton);
        clearOrderButton = findViewById(R.id.clearOrderButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
    }

    /**
     * Sets up the RecyclerView to display the list of pizzas in the current order.
     */
    private void setupRecyclerView() {
        pizzaAdapter = new PizzaAdapter(this, new ArrayList<>(currentOrder.getPizzas()));
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(pizzaAdapter);
    }

    /**
     * Sets up the button listeners for removing a pizza, clearing the order,
     * and placing the order.
     */
    private void setupButtonListeners() {
        removePizzaButton.setOnClickListener(v -> handleRemovePizza());
        clearOrderButton.setOnClickListener(v -> handleClearOrder());
        placeOrderButton.setOnClickListener(v -> handlePlaceOrder());
    }

    /**
     * Handles the removal of the selected pizza from the order.
     */
    private void handleRemovePizza() {
        Pizza selectedPizza = pizzaAdapter.getSelectedPizza();
        if (selectedPizza != null) {
            currentOrder.removePizza(selectedPizza);
            pizzaAdapter.updatePizzas(new ArrayList<>(currentOrder.getPizzas()));
            updateTotals();
            Toast.makeText(this, "Pizza removed.", Toast.LENGTH_SHORT).show();
        } else {
            showAlert("No Pizza Selected", "Please select a pizza to remove.");
        }
    }

    /**
     * Handles clearing the current order.
     */
    private void handleClearOrder() {
        currentOrder.clearOrder();
        pizzaAdapter.updatePizzas(new ArrayList<>(currentOrder.getPizzas()));
        updateTotals();
        Toast.makeText(this, "Order cleared.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Handles placing the current order and resetting the UI for a new order.
     */
    private void handlePlaceOrder() {
        if (!currentOrder.getPizzas().isEmpty()) {
            Log.d("OrderActivity", "Placing order: " + currentOrder.getOrderNumber());
            for (Pizza pizza : currentOrder.getPizzas()) {
                Log.d("OrderActivity", pizza.toString());
            }

            Intent intent = new Intent(this, OrderSummaryActivity.class);
            intent.putExtra("order", currentOrder);
            startActivity(intent);

            GlobalData.placeCurrentOrder(); // Add current order to placed orders
            currentOrder = GlobalData.getCurrentOrder(); // Reset to a new order

            pizzaAdapter.updatePizzas(new ArrayList<>(currentOrder.getPizzas()));
            updateOrderNumber();
            updateTotals();

            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        } else {
            showAlert("Order is Empty", "Cannot place an empty order.");
        }
    }

    /**
     * Updates the order number label with the current order's number.
     */
    private void updateOrderNumber() {
        orderNumberLabel.setText("Order Number: " + currentOrder.getOrderNumber());
    }

    /**
     * Updates the subtotal, sales tax, and total labels based on the current order.
     */
    private void updateTotals() {
        double subtotal = currentOrder.calculateTotal();
        double salesTax = subtotal * SALES_TAX_RATE;
        double total = subtotal + salesTax;

        subtotalLabel.setText(String.format("$%.2f", subtotal));
        salesTaxLabel.setText(String.format("$%.2f", salesTax));
        orderTotalLabel.setText(String.format("$%.2f", total));
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   the title of the alert dialog
     * @param message the message content of the alert dialog
     */
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
