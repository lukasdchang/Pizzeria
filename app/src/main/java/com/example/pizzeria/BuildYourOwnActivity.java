package com.example.pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.PizzaFactory;
import main.java.models.*;

/**
 * Controller class for the "Build Your Own" pizza view in the RU Pizzeria application.
 * Manages user interactions for creating custom pizzas, selecting toppings, setting pizza sizes,
 * and adding pizzas to the order.
 *
 * This controller interacts with the view via JavaFX elements and handles input changes, updates
 * the pizza details, and communicates with other controllers as needed.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class BuildYourOwnActivity {

    // FXML injected elements
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView pizzaImageView;
    @FXML
    private ComboBox<String> pizzaTypeDropdown;
    @FXML
    private RadioButton smallRadio;
    @FXML
    private RadioButton mediumRadio;
    @FXML
    private RadioButton largeRadio;
    @FXML
    private TextField crustTextField; // Read-only text field for crust display
    @FXML
    private ListView<Topping> availableToppingsList;
    @FXML
    private ListView<Topping> selectedToppingsList;
    @FXML
    private Button addToppingButton;
    @FXML
    private Button removeToppingButton;
    @FXML
    private Button addToOrderButton;
    @FXML
    private TextField priceTextField;

    private ToggleGroup sizeGroup; // Toggle group for radio buttons
    private ObservableList<Topping> availableToppings;
    private ObservableList<Topping> selectedToppings = FXCollections.observableArrayList();
    private String style; // Style of pizza (e.g., "Chicago" or "NY")
    private PizzaFactory pizzaFactory;
    private OrderActivity orderActivity;

    /**
     * Initializes the controller, setting default values, and configuring UI elements.
     * This method is called automatically after the FXML has been loaded.
     */
    public void initialize() {
        // Set default style and pizza factory
        setStyle("Chicago");

        // Populate available toppings list
        availableToppings = FXCollections.observableArrayList(
                Topping.SAUSAGE, Topping.BBQ_CHICKEN, Topping.BEEF, Topping.HAM,
                Topping.PEPPERONI, Topping.GREEN_PEPPER, Topping.ONION, Topping.MUSHROOM,
                Topping.OLIVE, Topping.SPINACH, Topping.PINEAPPLE, Topping.CHEDDAR, Topping.PROVOLONE
        );
        availableToppingsList.setItems(availableToppings);
        selectedToppingsList.setItems(selectedToppings);

        // Configure size selection radio buttons
        sizeGroup = new ToggleGroup();
        smallRadio.setToggleGroup(sizeGroup);
        mediumRadio.setToggleGroup(sizeGroup);
        largeRadio.setToggleGroup(sizeGroup);
        mediumRadio.setSelected(true); // Default size is medium

        // Add listeners to update price on size change
        smallRadio.setOnAction(e -> updatePrice());
        mediumRadio.setOnAction(e -> updatePrice());
        largeRadio.setOnAction(e -> updatePrice());

        // Configure pizza type dropdown
        pizzaTypeDropdown.setItems(FXCollections.observableArrayList(
                "Build your own", "Deluxe", "BBQ Chicken", "Meatzza"
        ));
        pizzaTypeDropdown.getSelectionModel().selectFirst(); // Default selection
        pizzaTypeDropdown.setOnAction(e -> handlePizzaTypeSelection());

        // Configure crust display and initial values
        crustTextField.setEditable(false); // Read-only field
        updateCrustText();
        updatePizzaImage();
        updatePrice();
    }

    /**
     * Handles adding a topping to the selected toppings list for "Build Your Own" pizzas.
     */
    @FXML
    private void handleAddTopping() {
        Topping selected = availableToppingsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (selectedToppings.contains(selected)) {
                showAlert("Duplicate Topping", "You have already selected this topping. Please choose a different topping.");
            } else if (selectedToppings.size() >= 7) {
                showAlert("Topping Limit Reached", "You can select up to 7 toppings only.");
            } else {
                selectedToppings.add(selected);
                updatePrice(); // Update price after adding a topping
            }
        }
    }

    /**
     * Handles removing a topping from the selected toppings list.
     */
    @FXML
    private void handleRemoveTopping() {
        Topping selected = selectedToppingsList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedToppings.remove(selected);
            updatePrice(); // Update price after removing a topping
        }
    }

    /**
     * Handles changes in the selected pizza type from the dropdown menu.
     * Updates crust, image, and toppings as needed.
     */
    @FXML
    private void handlePizzaTypeSelection() {
        String selectedType = pizzaTypeDropdown.getValue();
        updateCrustText();
        updatePizzaImage();
        updatePrice();

        if ("Build your own".equals(selectedType)) {
            availableToppingsList.setDisable(false); // Allow custom toppings
            selectedToppings.clear();
        } else {
            availableToppingsList.setDisable(true); // Disable topping selection for preset pizzas
            selectedToppings.clear();

            Pizza presetPizza = switch (selectedType) {
                case "BBQ Chicken" -> pizzaFactory.createBBQChicken();
                case "Deluxe" -> pizzaFactory.createDeluxe();
                case "Meatzza" -> pizzaFactory.createMeatzza();
                default -> null;
            };
            if (presetPizza != null) {
                selectedToppings.addAll(presetPizza.getToppings());
            }
        }
    }

    /**
     * Handles adding the currently configured pizza to the order.
     */
    @FXML
    private void handleAddToOrder() {
        Size selectedSize = smallRadio.isSelected() ? Size.SMALL :
                mediumRadio.isSelected() ? Size.MEDIUM :
                        Size.LARGE;

        Pizza pizza;
        String selectedType = pizzaTypeDropdown.getValue();
        if ("Build your own".equals(selectedType)) {
            String crustText = crustTextField.getText().toUpperCase().replace(" ", "_").replace("-", "_");

            try {
                // Pass style as the third argument
                pizza = new BuildYourOwn(Crust.valueOf(crustText), selectedSize, style);
                ((BuildYourOwn) pizza).getToppings().addAll(selectedToppings);
            } catch (IllegalArgumentException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Crust", "The selected crust type is not valid.");
                return;
            }
        } else {
            pizza = switch (selectedType) {
                case "BBQ Chicken" -> pizzaFactory.createBBQChicken();
                case "Deluxe" -> pizzaFactory.createDeluxe();
                case "Meatzza" -> pizzaFactory.createMeatzza();
                default -> null;
            };
            if (pizza != null) {
                pizza.setSize(selectedSize);
            }
        }

        if (pizza != null && orderActivity != null) {
            orderActivity.addPizzaToOrder(pizza);
            showAlert(Alert.AlertType.INFORMATION, "Pizza Added", "The pizza has been added to your order.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add pizza to the order.");
        }
    }

    /**
     * Updates the crust display text based on the selected pizza style and type.
     */
    private void updateCrustText() {
        if (style == null || pizzaTypeDropdown.getValue() == null) {
            crustTextField.setText("");
            return;
        }

        String selectedType = pizzaTypeDropdown.getValue();
        if ("Chicago".equals(style)) {
            switch (selectedType) {
                case "Deluxe" -> crustTextField.setText("Deep Dish");
                case "BBQ Chicken" -> crustTextField.setText("Pan");
                case "Meatzza" -> crustTextField.setText("Stuffed");
                case "Build your own" -> crustTextField.setText("Pan");
            }
        } else if ("NY".equals(style)) {
            switch (selectedType) {
                case "Deluxe" -> crustTextField.setText("Brooklyn");
                case "BBQ Chicken" -> crustTextField.setText("Thin");
                case "Meatzza" -> crustTextField.setText("Hand-tossed");
                case "Build your own" -> crustTextField.setText("Hand-tossed");
            }
        }
    }

    /**
     * Updates the pizza image based on the selected style and type.
     */
    private void updatePizzaImage() {
        if (style == null || pizzaTypeDropdown.getValue() == null) {
            return;
        }

        String selectedType = pizzaTypeDropdown.getValue().toLowerCase().replace(" ", "");
        String imagePath = String.format("/images/%s_%s.png", selectedType, style.toLowerCase());
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            pizzaImageView.setImage(image);
        } catch (Exception e) {
            // Image loading failed (silent handling)
        }
    }

    /**
     * Shows an alert message to the user.
     *
     * @param alertType the type of alert to show
     * @param title     the title of the alert
     * @param message   the message content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Convenience method to show an information alert.
     *
     * @param title   the title of the alert
     * @param message the message content of the alert
     */
    private void showAlert(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Updates the displayed price based on the selected pizza type, size, and toppings.
     */
    private void updatePrice() {
        String selectedType = pizzaTypeDropdown.getValue();
        Size selectedSize = smallRadio.isSelected() ? Size.SMALL :
                mediumRadio.isSelected() ? Size.MEDIUM :
                        Size.LARGE;
        double price = PriceCalculator.calculatePrice(selectedType, selectedSize, selectedToppings);
        priceTextField.setText(String.format("$%.2f", price));
    }

    /**
     * Sets the associated OrderActivity for managing pizza orders.
     *
     * @param orderActivity the OrderActivity instance
     */
    public void setOrderController(OrderActivity orderActivity) {
        this.orderActivity = orderActivity;
    }

    /**
     * Sets the style of the pizza ("Chicago" or "NY") and updates related UI elements.
     *
     * @param style the style of the pizza
     */
    public void setStyle(String style) {
        this.style = style != null ? style : "Chicago"; // Default to "Chicago" if style is null
        titleLabel.setText(style + " Style Pizza");
        pizzaFactory = style.equals("Chicago") ? new ChicagoPizza() : new NYPizza();
        updateCrustText();
        updatePizzaImage();
    }
}
