package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pizzeria.R;
import com.example.pizzeria.*;

public class MainMenuActivity extends AppCompatActivity {

    private Button chicagoStyleButton;
    private Button nyStyleButton;
    private Button ordersPlacedButton;
    private Button currentOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Initialize UI components
        chicagoStyleButton = findViewById(R.id.chicagoStyleButton);
        nyStyleButton = findViewById(R.id.nyStyleButton);
        ordersPlacedButton = findViewById(R.id.ordersPlacedButton);
        currentOrderButton = findViewById(R.id.currentOrderButton);

        // Set button listeners
        chicagoStyleButton.setOnClickListener(v -> openBuildYourOwnActivity("Chicago Style Pizza - Build Your Own", "Chicago"));
        nyStyleButton.setOnClickListener(v -> openBuildYourOwnActivity("NY Style Pizza - Build Your Own", "NY"));
        ordersPlacedButton.setOnClickListener(v -> handleOrderSummary());
        currentOrderButton.setOnClickListener(v -> handleOrderView());
    }

    /**
     * Opens the "Build Your Own" pizza activity for the specified style.
     *
     * @param title the title for the new activity
     * @param style the pizza style ("Chicago" or "NY")
     */
    private void openBuildYourOwnActivity(String title, String style) {
        Intent intent = new Intent(MainMenuActivity.this, BuildYourOwnActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("style", style);
        startActivity(intent);
    }

    /**
     * Handles navigation to the Order Summary Activity.
     */
    private void handleOrderSummary() {
        // Assuming OrderSummaryActivity handles displaying all orders
        Intent intent = new Intent(MainMenuActivity.this, OrderSummaryActivity.class);
        startActivity(intent);
    }

    /**
     * Handles navigation to the Current Order Activity.
     */
    private void handleOrderView() {
        // Assuming OrderActivity handles displaying the current order
        Intent intent = new Intent(MainMenuActivity.this, OrderActivity.class);
        startActivity(intent);
    }
}