package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;
import android.app.AlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.util.Locale;
import com.example.pizzeria.R;
import com.example.pizzeria.models.*;
import com.example.pizzeria.adapters.*;

import java.util.ArrayList;

public class BuildYourOwnActivity extends AppCompatActivity {

    private TextView titleLabel;
    private ImageView pizzaImageView;
    private Spinner pizzaTypeSpinner;
    private RadioButton smallRadio, mediumRadio, largeRadio;
    private TextView crustTextView;
    private RecyclerView availableToppingsRecyclerView, selectedToppingsRecyclerView;
    private Button addToppingButton, removeToppingButton, addToOrderButton;
    private TextView priceTextView;

    private ArrayList<Topping> availableToppings;
    private ArrayList<Topping> selectedToppings;
    private ToppingsAdapter availableToppingsAdapter, selectedToppingsAdapter;

    private String style = "Chicago"; // Default style
    private PizzaFactory pizzaFactory;
    private Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_your_own);

        String title = getIntent().getStringExtra("title");
        style = getIntent().getStringExtra("style");

        // Find and set the titleLabel
        titleLabel = findViewById(R.id.titleLabel);
        if (title != null) {
            titleLabel.setText(title); // Set title from Intent
        }

        // Enable the back button in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize UI components
        initializeUIComponents();

        // Populate pizza type Spinner
        setupPizzaTypeSpinner();

        // Set default style and pizza factory
        if (style != null) {
            setStyle(style);
        }

        // Populate toppings
        populateToppings();

        // Configure adapters
        setupRecyclerViews();

        // Set up event listeners
        setupEventListeners();

        updatePizzaImage();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click (just like your current backButton functionality)
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeUIComponents() {
        titleLabel = findViewById(R.id.titleLabel);
        pizzaImageView = findViewById(R.id.pizzaImageView);
        pizzaTypeSpinner = findViewById(R.id.pizzaTypeSpinner);
        smallRadio = findViewById(R.id.smallRadio);
        mediumRadio = findViewById(R.id.mediumRadio);
        largeRadio = findViewById(R.id.largeRadio);
        crustTextView = findViewById(R.id.crustTextView);
        availableToppingsRecyclerView = findViewById(R.id.availableToppingsRecyclerView);
        selectedToppingsRecyclerView = findViewById(R.id.selectedToppingsRecyclerView);
        addToppingButton = findViewById(R.id.addToppingButton);
        removeToppingButton = findViewById(R.id.removeToppingButton);
        addToOrderButton = findViewById(R.id.addToOrderButton);
        priceTextView = findViewById(R.id.priceTextView);
    }

    private void setupPizzaTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pizza_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(adapter);

        // Handle item selection
        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handlePizzaTypeSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });
    }

    private void setStyle(String style) {
        this.style = style;
        titleLabel.setText(style + " Style Pizza");
        pizzaFactory = style.equals("Chicago") ? new ChicagoPizza() : new NYPizza();
        updateCrustText();
        updatePizzaImage();
    }

    private void populateToppings() {
        availableToppings = new ArrayList<>();
        selectedToppings = new ArrayList<>();
        availableToppings.add(Topping.SAUSAGE);
        availableToppings.add(Topping.BBQ_CHICKEN);
        availableToppings.add(Topping.BEEF);
        availableToppings.add(Topping.HAM);
        availableToppings.add(Topping.PEPPERONI);
        availableToppings.add(Topping.GREEN_PEPPER);
        availableToppings.add(Topping.ONION);
        availableToppings.add(Topping.MUSHROOM);
        availableToppings.add(Topping.OLIVE);
        availableToppings.add(Topping.SPINACH);
        availableToppings.add(Topping.PINEAPPLE);
        availableToppings.add(Topping.CHEDDAR);
        availableToppings.add(Topping.PROVOLONE);
    }

    private void setupRecyclerViews() {
        availableToppingsAdapter = new ToppingsAdapter(this, availableToppings, false);
        selectedToppingsAdapter = new ToppingsAdapter(this, selectedToppings, true);

        availableToppingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        availableToppingsRecyclerView.setAdapter(availableToppingsAdapter);

        selectedToppingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectedToppingsRecyclerView.setAdapter(selectedToppingsAdapter);
    }
    private void setupEventListeners() {
        addToppingButton.setOnClickListener(v -> handleAddTopping());
        removeToppingButton.setOnClickListener(v -> handleRemoveTopping());
        addToOrderButton.setOnClickListener(v -> handleAddToOrder());

        pizzaTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handlePizzaTypeSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        smallRadio.setOnClickListener(v -> updatePrice());
        mediumRadio.setOnClickListener(v -> updatePrice());
        largeRadio.setOnClickListener(v -> updatePrice());
    }

    private void handleAddTopping() {
        Topping selected = availableToppingsAdapter.getSelectedTopping();
        if (selected == null) {
            showToast("Please select a topping to add.");
        } else if (selectedToppings.contains(selected)) {
            showAlert("Duplicate Topping", "You have already selected this topping.");
        } else if (selectedToppings.size() >= 7) {
            showAlert("Topping Limit Reached", "You can select up to 7 toppings only.");
        } else {
            selectedToppings.add(selected);
            selectedToppingsAdapter.notifyDataSetChanged();
            updatePrice();
            showToast("Selected topping: " + selected.name()); // Show toast only after adding
        }
    }

    private void handleRemoveTopping() {
        Topping selected = selectedToppingsAdapter.getSelectedTopping();
        if (selected != null) {
            selectedToppings.remove(selected);
            selectedToppingsAdapter.notifyDataSetChanged();
            updatePrice();
        }
    }

    private void handlePizzaTypeSelection(int position) {
        String selectedType = pizzaTypeSpinner.getSelectedItem().toString();

        if ("Build your own".equals(selectedType)) {
            availableToppingsAdapter.enableSelection(); // Allow selecting toppings
            availableToppingsRecyclerView.setAlpha(1.0f);
            availableToppingsRecyclerView.setEnabled(true);

            selectedToppings.clear();
            selectedToppingsAdapter.notifyDataSetChanged();
        } else {
            availableToppingsAdapter.disableSelection(); // Disable selecting toppings
            availableToppingsRecyclerView.setAlpha(0.5f); // Grey out RecyclerView
            availableToppingsRecyclerView.setEnabled(false); // Disable interaction

            selectedToppings.clear();

            Pizza presetPizza = null;
            switch (selectedType) {
                case "BBQ Chicken":
                    presetPizza = pizzaFactory.createBBQChicken();
                    break;
                case "Deluxe":
                    presetPizza = pizzaFactory.createDeluxe();
                    break;
                case "Meatzza":
                    presetPizza = pizzaFactory.createMeatzza();
                    break;
            }

            if (presetPizza != null) {
                selectedToppings.addAll(presetPizza.getToppings());
                selectedToppingsAdapter.notifyDataSetChanged();
            }
        }

        // Update the image when the type is selected
        updatePizzaImage();
        updateCrustText();
        updatePrice();
    }

    private void handleAddToOrder() {
        // Determine the selected size using the RadioButton
        Size selectedSize;
        if (smallRadio.isChecked()) {
            selectedSize = Size.SMALL;
        } else if (mediumRadio.isChecked()) {
            selectedSize = Size.MEDIUM;
        } else {
            selectedSize = Size.LARGE;
        }

        Pizza pizza = null; // Initialize a Pizza object
        String selectedType = pizzaTypeSpinner.getSelectedItem().toString(); // Get selected type from Spinner

        try {
            if ("Build your own".equals(selectedType)) {
                // Handle "Build Your Own" pizza type
                String crustText = crustTextView.getText().toString().toUpperCase().replace(" ", "_").replace("-", "_");

                pizza = new BuildYourOwn(Crust.valueOf(crustText), selectedSize, style);
                // Add selected toppings
                ((BuildYourOwn) pizza).getToppings().addAll(selectedToppings);
            } else {
                // Handle preset pizzas
                switch (selectedType) {
                    case "BBQ Chicken":
                        pizza = pizzaFactory.createBBQChicken();
                        break;
                    case "Deluxe":
                        pizza = pizzaFactory.createDeluxe();
                        break;
                    case "Meatzza":
                        pizza = pizzaFactory.createMeatzza();
                        break;
                }
                if (pizza != null) {
                    pizza.setSize(selectedSize);
                }
            }
        } catch (IllegalArgumentException e) {
            showAlert("Invalid Crust", "The selected crust type is not valid.");
            return;
        }

        if (pizza != null) {
            // Add pizza to the current order and show confirmation
            if (currentOrder == null) {
                currentOrder = new Order();
            }
            currentOrder.addPizza(pizza);
            GlobalData.addPlacedOrder(currentOrder);

            showToast("Pizza has been added to your order.");
        } else {
            showAlert("Error", "Failed to add pizza to the order.");
        }
    }

    private void updateCrustText() {
        String selectedType = pizzaTypeSpinner.getSelectedItem().toString();
        if ("Chicago".equals(style)) {
            switch (selectedType) {
                case "Deluxe":
                    crustTextView.setText("Deep Dish");
                    break;
                case "BBQ Chicken":
                    crustTextView.setText("Pan");
                    break;
                case "Meatzza":
                    crustTextView.setText("Stuffed");
                    break;
                case "Build your own":
                    crustTextView.setText("Pan");
                    break;
            }
        } else if ("NY".equals(style)) {
            switch (selectedType) {
                case "Deluxe":
                    crustTextView.setText("Brooklyn");
                    break;
                case "BBQ Chicken":
                    crustTextView.setText("Thin");
                    break;
                case "Meatzza":
                    crustTextView.setText("Hand-tossed");
                    break;
                case "Build your own":
                    crustTextView.setText("Hand-tossed");
                    break;
            }
        }
    }

    private void updatePizzaImage() {
        // Get the selected pizza type from the Spinner
        String selectedType = pizzaTypeSpinner.getSelectedItem().toString().toLowerCase().replace(" ", "");

        // Use the current style (e.g., "Chicago" or "NY")
        String currentStyle = style.toLowerCase();

        // Build the image name based on the naming convention
        String imageName = String.format("img_%s_%s", selectedType, currentStyle);

        // Retrieve the resource ID of the image
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        // Check if the resource exists and set the ImageView
        if (imageResId != 0) {
            pizzaImageView.setImageResource(imageResId);
        } else {
            // Fallback: If the image is missing, set a default placeholder image
            pizzaImageView.setImageResource(R.drawable.placeholder); // Add a placeholder image in `drawable`
        }
    }

    private void updatePrice() {
        // Get the selected pizza type from the Spinner
        String selectedType = pizzaTypeSpinner.getSelectedItem().toString();

        // Determine the selected size using the RadioButtons
        Size selectedSize;
        if (smallRadio.isChecked()) {
            selectedSize = Size.SMALL;
        } else if (mediumRadio.isChecked()) {
            selectedSize = Size.MEDIUM;
        } else {
            selectedSize = Size.LARGE;
        }

        // Calculate the price using the PriceCalculator
        double price;
        if ("Build your own".equals(selectedType)) {
            // For "Build Your Own", calculate base price + topping price
            price = PriceCalculator.calculatePrice(
                    "Build Your Own",
                    selectedSize,
                    selectedToppings // Pass the selected toppings to the method
            );
        } else {
            // For preset pizzas, calculate based on type and size
            price = PriceCalculator.calculatePrice(
                    selectedType, // Preset pizza type (e.g., "Deluxe", "BBQ Chicken")
                    selectedSize,
                    null // No toppings for preset pizzas
            );
        }

        // Update the price TextView
        priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", price));
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}