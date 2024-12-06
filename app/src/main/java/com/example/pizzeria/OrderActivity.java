package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import android.view.MenuItem;

import com.example.pizzeria.R;
import com.example.pizzeria.adapters.PizzaAdapter;
import com.example.pizzeria.models.Order;
import com.example.pizzeria.models.Pizza;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    private TextView orderNumberLabel;
    private RecyclerView orderRecyclerView;
    private TextView subtotalLabel, salesTaxLabel, orderTotalLabel;
    private Button removePizzaButton, clearOrderButton, placeOrderButton;

    private Order currentOrder;
    private PizzaAdapter pizzaAdapter;

    private static final double SALES_TAX_RATE = 0.06625; // New Jersey sales tax

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

        // Use the existing order from GlobalData
        currentOrder = GlobalData.getCurrentOrder();

        // Set up RecyclerView and other components
        setupRecyclerView();
        updateOrderNumber();
        updateTotals();
        setupButtonListeners();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Go back to the main menu when the back button is pressed
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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

    private void initializeOrder() {
        currentOrder = new Order();
        updateOrderNumber();
        updateTotals();
    }

    private void setupRecyclerView() {
        pizzaAdapter = new PizzaAdapter(this, new ArrayList<>(currentOrder.getPizzas()));
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setAdapter(pizzaAdapter);
    }

    private void setupButtonListeners() {
        removePizzaButton.setOnClickListener(v -> handleRemovePizza());
        clearOrderButton.setOnClickListener(v -> handleClearOrder());
        placeOrderButton.setOnClickListener(v -> handlePlaceOrder());
    }

    private void handleRemovePizza() {
        Pizza selectedPizza = pizzaAdapter.getSelectedPizza();
        if (selectedPizza != null) {
            currentOrder.removePizza(selectedPizza);
            pizzaAdapter.updatePizzas(new ArrayList<>(currentOrder.getPizzas()));
            updateTotals();
        } else {
            showAlert("No pizza selected", "Please select a pizza to remove.");
        }
    }

    private void handleClearOrder() {
        currentOrder.clearOrder();
        pizzaAdapter.updatePizzas(new ArrayList<>(currentOrder.getPizzas()));
        updateTotals();
    }

    private void handlePlaceOrder() {
        if (!currentOrder.getPizzas().isEmpty()) {
            Intent intent = new Intent(this, OrderSummaryActivity.class);
            intent.putExtra("order", currentOrder);
            startActivity(intent);

            // Reset the order only after placing it
            GlobalData.resetCurrentOrder();
            pizzaAdapter.updatePizzas(new ArrayList<>(GlobalData.getCurrentOrder().getPizzas()));
            updateOrderNumber();
            updateTotals();
        } else {
            showAlert("Order is empty", "Cannot place an empty order.");
        }
    }

    private void updateOrderNumber() {
        orderNumberLabel.setText("Order Number: " + currentOrder.getOrderNumber());
    }

    private void updateTotals() {
        double subtotal = currentOrder.calculateTotal();
        double salesTax = subtotal * SALES_TAX_RATE;
        double total = subtotal + salesTax;

        subtotalLabel.setText(String.format("$%.2f", subtotal));
        salesTaxLabel.setText(String.format("$%.2f", salesTax));
        orderTotalLabel.setText(String.format("$%.2f", total));
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}