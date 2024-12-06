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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.adapters.PizzaAdapter;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Pizza;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    private Spinner orderNumberDropdown;
    private ListView orderDetailsListView;
    private TextView orderTotalLabel;
    private Button cancelOrderButton, exportOrdersButton;

    private List<Order> orders;
    private List<Integer> orderNumbers;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click
            finish(); // Closes this activity and returns to the previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeUIComponents() {
        orderNumberDropdown = findViewById(R.id.orderNumberDropdown);
        orderDetailsListView = findViewById(R.id.orderDetailsListView);
        orderTotalLabel = findViewById(R.id.orderTotalLabel);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        exportOrdersButton = findViewById(R.id.exportOrdersButton);
    }

    private void initializeData() {
        // Retrieve orders from a global data source or in-memory simulation
        orders = GlobalData.getPlacedOrders(); // Replace with actual data source when implemented

        // Populate order numbers
        orderNumbers = new ArrayList<>();
        for (Order order : orders) {
            orderNumbers.add(order.getOrderNumber());
        }

        if (orders.isEmpty()) {
            Toast.makeText(this, "No orders available.", Toast.LENGTH_SHORT).show();
        }
    }

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

    private void setupButtonListeners() {
        cancelOrderButton.setOnClickListener(v -> handleCancelOrder());
        exportOrdersButton.setOnClickListener(v -> handleExportOrders());
    }

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
                    android.R.layout.simple_list_item_1, // Use a built-in simple layout
                    pizzaDescriptions
            );
            orderDetailsListView.setAdapter(adapter);

            // Update the total with tax
            double totalWithTax = selectedOrder.calculateTotalWithTax();
            orderTotalLabel.setText(String.format("Order Total: $%.2f", totalWithTax));
        }
    }

    private void handleCancelOrder() {
        // Get the selected order number from the dropdown
        Integer selectedOrderNumber = (Integer) orderNumberDropdown.getSelectedItem();
        if (selectedOrderNumber == null) {
            showAlert("Error", "No order selected.");
            return;
        }

        // Find the order to remove
        Order orderToRemove = findOrderByNumber(selectedOrderNumber);
        if (orderToRemove != null) {
            // Remove the order from the lists
            orders.remove(orderToRemove);
            orderNumbers.remove(selectedOrderNumber);

            // Show confirmation message
            Toast.makeText(this, "Order " + selectedOrderNumber + " canceled.", Toast.LENGTH_SHORT).show();

            // Refresh the dropdown
            setupOrderDropdown();

            // Clear the ListView and reset the total label
            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    new ArrayList<>()
            );
            orderDetailsListView.setAdapter(emptyAdapter);

            orderTotalLabel.setText("Order Total: $0.00");
        } else {
            showAlert("Error", "Order not found.");
        }
    }

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

    private Order findOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}