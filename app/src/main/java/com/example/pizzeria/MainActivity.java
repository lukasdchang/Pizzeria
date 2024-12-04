package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Entry point for the Pizzeria Android application.
 * This class extends the Android `AppCompatActivity` class and is responsible
 * for launching the main menu activity.
 *
 * The application launches a GUI window using an XML layout for the UI.
 *
 * @author Your Name
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Sets up the initial UI and launches the main menu.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Launch the MainMenuActivity
        Intent intent = new Intent(MainActivity.this, MainMenuActivity.class);
        startActivity(intent);

        // Finish MainActivity so it doesn't remain in the back stack
        finish();
    }
}