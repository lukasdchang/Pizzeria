package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private ImageButton chicagoStyleButton;
    private ImageButton nyStyleButton;
    private ImageButton ordersPlacedButton;
    private ImageButton currentOrderButton;

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
        Intent intent = new Intent(MainMenuActivity.this, OrderSummaryActivity.class);
        startActivity(intent);
    }

    /**
     * Handles navigation to the Current Order Activity.
     */
    private void handleOrderView() {
        Intent intent = new Intent(MainMenuActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    /**
     * Initializes the main menu activity, sets up the UI components, and defines button actions
     * for navigating to various activities like Build Your Own Pizza, Current Order,
     * and Order Summary.
     *
     * @param savedInstanceState the previously saved instance state, if available
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Initialize UI components as ImageButtons
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
}