package com.example.pizzeria;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.adapters.PizzaAdapter;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Pizza;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    private Spinner orderNumberDropdown;
    private RecyclerView orderDetailsRecyclerView;
    private TextView orderTotalLabel;
    private Button cancelOrderButton, exportOrdersButton;

    private List<Order> orders;
    private List<Integer> orderNumbers;
    private PizzaAdapter pizzaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Initialize UI components
        initializeUIComponents();

        // Initialize data
        initializeData();

        // Set up dropdown
        setupOrderDropdown();

        // Set button listeners
        setupButtonListeners();
    }

    private void initializeUIComponents() {
        orderNumberDropdown = findViewById(R.id.orderNumberDropdown);
        orderDetailsRecyclerView = findViewById(R.id.orderDetailsRecyclerView);
        orderTotalLabel = findViewById(R.id.orderTotalLabel);
        cancelOrderButton = findViewById(R.id.cancelOrderButton);
        exportOrdersButton = findViewById(R.id.exportOrdersButton);
    }

    private void initializeData() {
        // Retrieve orders from a global data source or in-memory simulation
        orders = GlobalData.getOrders(); // Replace with actual data source when implemented

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
            pizzaAdapter = new PizzaAdapter(this, selectedOrder.getPizzas());
            orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            orderDetailsRecyclerView.setAdapter(pizzaAdapter);

            double totalWithTax = selectedOrder.calculateTotalWithTax();
            orderTotalLabel.setText(String.format("$%.2f", totalWithTax));
        }
    }

    private void handleCancelOrder() {
        int selectedOrderNumber = (int) orderNumberDropdown.getSelectedItem();
        Order orderToRemove = findOrderByNumber(selectedOrderNumber);
        if (orderToRemove != null) {
            orders.remove(orderToRemove);
            orderNumbers.remove(Integer.valueOf(selectedOrderNumber));

            Toast.makeText(this, "Order " + selectedOrderNumber + " canceled.", Toast.LENGTH_SHORT).show();

            // Refresh UI
            setupOrderDropdown();
            orderDetailsRecyclerView.setAdapter(null);
            orderTotalLabel.setText("$0.00");
        } else {
            showAlert("Error", "No order selected.");
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