package com.example.pizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller class for the main menu of the RU Pizzeria application.
 * Manages user interactions from the main menu and provides navigation
 * to other parts of the application such as Chicago-style and NY-style pizzas,
 * current orders, and order summaries.
 *
 * Handles UI behavior, including highlighting buttons, opening different views,
 * and managing controller communication.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class MainMenuActivity {

    // FXML injected buttons for navigation and control
    @FXML
    private Button chicagoStyleButton;
    @FXML
    private Button nyStyleButton;
    @FXML
    private Button ordersPlacedButton;
    @FXML
    private Button currentOrderButton;

    // Optional references for inter-controller communication
    private OrderSummaryActivity orderSummaryActivity;
    private OrderActivity orderActivity; // Reference to the OrderActivity
    private Pane orderViewPane; // Cached view of OrderView
    private Stage orderViewStage; // Stage for displaying OrderView
    private Stage orderSummaryStage; // Stage for displaying OrderSummary

    /**
     * Initializes the controller and sets up initial UI elements, loads views,
     * and establishes communication between controllers.
     * This method is automatically called after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        // Load images for buttons
        setButtonImage(chicagoStyleButton, "/images/chicago_style.png");
        setButtonImage(nyStyleButton, "/images/ny_style.png");
        setButtonImage(ordersPlacedButton, "/images/orders_summary.png");
        setButtonImage(currentOrderButton, "/images/current_order.png");

        // Initialize controllers for other views
        initializeOrderController();
        initializeOrderSummaryController();

        // Establish inter-controller communication
        if (orderActivity != null && orderSummaryActivity != null) {
            orderActivity.setOrderSummaryController(orderSummaryActivity);
        }
    }

    /**
     * Highlights a button when the mouse hovers over it.
     *
     * @param event the mouse event triggered by hovering
     */
    @FXML
    private void highlightButton(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: #ffadad;");
    }

    /**
     * Resets the button's style when the mouse is no longer hovering over it.
     *
     * @param event the mouse event triggered by exiting hover state
     */
    @FXML
    private void resetButton(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("");
    }

    /**
     * Handles navigation to the "Build Your Own" Chicago-style pizza view.
     */
    @FXML
    private void handleChicagoPizza() {
        openBuildYourOwnController("Chicago Style Pizza - Build Your Own", "Chicago");
    }

    /**
     * Handles navigation to the "Build Your Own" NY-style pizza view.
     */
    @FXML
    private void handleNYPizza() {
        openBuildYourOwnController("NY Style Pizza - Build Your Own", "NY");
    }

    /**
     * Handles displaying the order summary window.
     */
    @FXML
    private void handleOrderSummary() {
        if (orderSummaryStage != null) {
            orderSummaryStage.show(); // Show existing stage
            orderSummaryStage.toFront(); // Bring to front
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Order Summary could not be loaded.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Handles displaying the current order view.
     */
    @FXML
    private void handleOrderView() {
        if (orderViewStage != null) {
            orderViewStage.show(); // Show existing stage
            orderViewStage.toFront(); // Bring to front
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "OrderView could not be loaded.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Initializes the OrderActivity by loading the associated FXML view.
     */
    private void initializeOrderController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OrderView.fxml"));
            orderViewPane = loader.load(); // Load the view
            orderActivity = loader.getController(); // Retrieve the controller instance

            // Set up the stage but do not show it yet
            orderViewStage = new Stage();
            orderViewStage.setTitle("Order Cart");
            orderViewStage.setScene(new Scene(orderViewPane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the OrderSummaryActivity by loading the associated FXML view.
     */
    private void initializeOrderSummaryController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OrderSummaryView.fxml"));
            Pane pane = loader.load();
            orderSummaryActivity = loader.getController(); // Store the controller instance

            // Set up the stage but do not show it yet
            orderSummaryStage = new Stage();
            orderSummaryStage.setTitle("Order Summary");
            orderSummaryStage.setScene(new Scene(pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the image for a given button from the specified image path.
     *
     * @param button the button to set the image for
     * @param imagePath the path to the image resource
     */
    private void setButtonImage(Button button, String imagePath) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        button.setGraphic(imageView);
    }

    /**
     * Opens the "Build Your Own" pizza view with the specified style.
     *
     * @param title the title for the new window
     * @param style the pizza style ("Chicago" or "NY")
     */
    private void openBuildYourOwnController(String title, String style) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BuildYourOwnView.fxml"));
            Pane pane = loader.load();

            // Get the controller instance
            BuildYourOwnActivity controller = loader.getController();
            controller.setStyle(style); // Set pizza style
            controller.setOrderController(orderActivity); // Pass OrderActivity reference

            // Set up and display the stage
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}