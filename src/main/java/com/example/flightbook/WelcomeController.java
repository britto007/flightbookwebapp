package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class WelcomeController {
    @FXML
    private Button getStartedButton;

    @FXML
    protected void onGetStartedClick() {
        try {
            // Load the login view
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/login-view.fxml"));
            if (fxmlLoader.getLocation() == null) {
                System.err.println("Error: Could not find login-view.fxml resource");
                return;
            }
            Parent loginRoot = fxmlLoader.load();
            
            // Get the current stage
            Stage stage = (Stage) getStartedButton.getScene().getWindow();
            
            // Set the new scene
            Scene loginScene = new Scene(loginRoot, 1000, 700);
            stage.setScene(loginScene);
            stage.setTitle("Login - Flight Booking");
        } catch (IOException e) {
            System.err.println("Error loading login view: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

