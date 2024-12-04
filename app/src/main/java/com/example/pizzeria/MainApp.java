package com.example.pizzeria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the RU Pizzeria JavaFX application.
 * This class extends the JavaFX `Application` class and is responsible
 * for setting up the primary stage and loading the main menu view.
 *
 * The application launches a GUI window using an FXML file for the layout.
 *
 * @author Yousef Naam & Lukas Chang
 */
public class MainApp extends Application {
    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 600;

    /**
     * Starts the JavaFX application by setting up the primary stage with
     * the specified FXML view.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if loading the FXML file fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the main menu view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainMenuView.fxml"));
        Parent root = loader.load();

        // Configure and display the primary stage
        primaryStage.setTitle("RU Pizzeria - Pizza View");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT)); // Set dimensions for the scene
        primaryStage.centerOnScreen(); // Center the stage on the screen
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args); // Launches the JavaFX application
    }
}