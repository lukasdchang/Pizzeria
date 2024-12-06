package com.example.pizzeria;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Pizza;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderSummaryActivity displays a summary of all placed orders.
 * Users can:
 * - View order details for selected orders.
 * - Cancel a specific order.
 * - Export all orders to a text file.
 *
 * This activity retrieves data from a global data source and updates the UI dynamically.
 * It includes a Spinner for order selection, a ListView for displaying order details,
 * and buttons for order management.
 *
 * File export functionality allows saving order summaries to a text file.
 *
 * @author You
 */
public class OrderSummaryActivity extends AppCompatActivity {

    // UI Components
    private Spinner orderNumberDropdown;
    private ListView orderDetailsListView;
    private TextView orderTotalLabel;
    private Button cancelOrderButton, exportOrdersButton;

    // Data Models
    private List<Order> orders;
    private List<Integer> orderNumbers;

    /**
     * Initializes the activity, sets up the UI components, and populates order data.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this contains the data
     *                           it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Set up action bar with back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Summary");
        }

        // Initialize UI components
        initializeUIComponents();

        // Initialize data
        initializeData();

        // Set up dropdown
        setupOrderDropdown();

        // Set button listeners
        setupButtonListeners();
    }

    /**
     * Handles the action bar's back button click to close the activity and return to the previous screen.
     *
     * @param item the selected menu item
     * @return true if the action is handled, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Closes this activity and returns to the previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes UI components by linking them to their XML counterparts.
     */
    private void initializeUIComponents() {
        orderNumberDropdown = findViewById(R.id.orderNumberDropdown);
        orderDetailsListView = findViewById(R.id.orderDetailsListView);
        orderTotalLabel = findViewById(R.id.orderTotalLabel);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        exportOrdersButton = findViewById(R.id.exportOrdersButton);
    }

    /**
     * Initializes order data from the global data source.
     * Retrieves placed orders and populates a list of order numbers for the dropdown menu.
     */
    private void initializeData() {
        orders = GlobalData.getPlacedOrders(); // Retrieve placed orders from the global data source
        orderNumbers = new ArrayList<>();

        for (Order order : orders) {
            orderNumbers.add(order.getOrderNumber());
        }

        if (orders.isEmpty()) {
            Toast.makeText(this, "No orders available.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets up the Spinner dropdown menu with order numbers.
     */
    private void setupOrderDropdown() {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, orderNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNumberDropdown.setAdapter(adapter);

        // Listener for dropdown selection
        orderNumberDropdown.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                handleOrderSelection(orderNumbers.get(position));
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Sets up button listeners for canceling orders and exporting order summaries.
     */
    private void setupButtonListeners() {
        cancelOrderButton.setOnClickListener(v -> handleCancelOrder());
        exportOrdersButton.setOnClickListener(v -> handleExportOrders());
    }

    /**
     * Handles the selection of an order from the dropdown menu.
     * Displays the order's details in the ListView and updates the total amount with tax.
     *
     * @param orderNumber The selected order's number
     */
    private void handleOrderSelection(int orderNumber) {
        Order selectedOrder = findOrderByNumber(orderNumber);
        if (selectedOrder != null) {
            // Convert pizzas to a string list for the ListView
            List<String> pizzaDescriptions = new ArrayList<>();
            for (Pizza pizza : selectedOrder.getPizzas()) {
                pizzaDescriptions.add(pizza.toString());
            }

            // Set up the ListView adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    pizzaDescriptions
            );
            orderDetailsListView.setAdapter(adapter);

            // Update the total with tax
            double totalWithTax = selectedOrder.calculateTotalWithTax();
            orderTotalLabel.setText(String.format("Order Total: $%.2f", totalWithTax));
        }
    }

    /**
     * Handles the cancellation of the selected order.
     * Removes the order from the global data and updates the UI.
     */
    private void handleCancelOrder() {
        Integer selectedOrderNumber = (Integer) orderNumberDropdown.getSelectedItem();
        if (selectedOrderNumber == null) {
            showAlert("Error", "No order selected.");
            return;
        }

        Order orderToRemove = findOrderByNumber(selectedOrderNumber);
        if (orderToRemove != null) {
            orders.remove(orderToRemove);
            orderNumbers.remove(selectedOrderNumber);

            Toast.makeText(this, "Order " + selectedOrderNumber + " canceled.", Toast.LENGTH_SHORT).show();

            setupOrderDropdown();

            // Clear the ListView and reset the total label
            orderDetailsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>()));
            orderTotalLabel.setText("Order Total: $0.00");
        } else {
            showAlert("Error", "Order not found.");
        }
    }

    /**
     * Handles the export of all orders to a text file in the app's internal storage.
     */
    private void handleExportOrders() {
        if (orders.isEmpty()) {
            showAlert("No Orders", "There are no orders to export.");
            return;
        }

        try (FileWriter writer = new FileWriter(getFilesDir() + "/orders.txt")) {
            for (Order order : orders) {
                writer.write("Order Number: " + order.getOrderNumber() + "\n");
                for (Pizza pizza : order.getPizzas()) {
                    writer.write(pizza.toString() + "\n");
                }
                writer.write(String.format("Total with Tax: $%.2f\n", order.calculateTotalWithTax()));
                writer.write("\n");
            }
            Toast.makeText(this, "Orders exported to " + getFilesDir() + "/orders.txt", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            showAlert("Error", "Failed to export orders.");
            e.printStackTrace();
        }
    }

    /**
     * Finds an order by its number.
     *
     * @param orderNumber The order number to search for
     * @return The matching Order object, or null if not found
     */
    private Order findOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert
     * @param message The message content of the alert
     */
    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
