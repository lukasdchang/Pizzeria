package com.example.pizzeria;

import android.os.Bundle;
import android.widget.*;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzeria.R;
import com.example.pizzeria.models.*;

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

        // Initialize UI components
        initializeUIComponents();

        // Set default style and pizza factory
        setStyle(style);

        // Populate toppings
        populateToppings();

        // Configure adapters
        setupRecyclerViews();

        // Set up event listeners
        setupEventListeners();
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
                handlePizzaTypeSelection();
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

    private void handlePizzaTypeSelection() {
        String selectedType = pizzaTypeSpinner.getSelectedItem().toString();
        updateCrustText();
        updatePizzaImage();
        updatePrice();

        if ("Build Your Own".equals(selectedType)) {
            availableToppingsAdapter.enableSelection();
            selectedToppings.clear();
            selectedToppingsAdapter.notifyDataSetChanged();
        } else {
            availableToppingsAdapter.disableSelection();
            selectedToppings.clear();
            selectedToppingsAdapter.notifyDataSetChanged();

            Pizza presetPizza = switch (selectedType) {
                case "BBQ Chicken" -> pizzaFactory.createBBQChicken();
                case "Deluxe" -> pizzaFactory.createDeluxe();
                case "Meatzza" -> pizzaFactory.createMeatzza();
                default -> null;
            };
            if (presetPizza != null) {
                selectedToppings.addAll(presetPizza.getToppings());
                selectedToppingsAdapter.notifyDataSetChanged();
            }
        }
    }

    private void handleAddToOrder() {
        // Add pizza to current order
    }

    private void updateCrustText() {
        // Update crust text based on pizza type and style
    }

    private void updatePizzaImage() {
        // Update pizza image based on type and style
    }

    private void updatePrice() {
        // Calculate and update price
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