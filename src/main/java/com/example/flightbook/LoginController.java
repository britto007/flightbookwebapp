package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField userIdField;
    
    @FXML
    private PasswordField userPasswordField;
    
    @FXML
    private TextField adminIdField;
    
    @FXML
    private PasswordField adminPasswordField;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Button backButton;
    
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();

    @FXML
    protected void onUserLogin() throws IOException {
        String username = userIdField.getText().trim();
        String password = userPasswordField.getText().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both User ID and Password");
            statusLabel.setStyle("-fx-text-fill: #d32f2f;");
            return;
        }
        
        // Check if user exists
        UserItem user = dbHelper.getUserByUsername(username);
        if (user != null) {
            // User exists, check password
            if (user.getPassword().equals(password)) {
                // Navigate to user dashboard
                navigateToUserDashboard(username);
            } else {
                statusLabel.setText("Invalid password");
                statusLabel.setStyle("-fx-text-fill: #d32f2f;");
            }
        } else {
            // New user, create account
            if (dbHelper.createUser(username, password)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Account Created");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully!");
                alert.showAndWait();
                navigateToUserDashboard(username);
            } else {
                statusLabel.setText("Error creating account");
                statusLabel.setStyle("-fx-text-fill: #d32f2f;");
            }
        }
    }

    @FXML
    protected void onAdminLogin() throws IOException {
        String adminId = adminIdField.getText().trim();
        String password = adminPasswordField.getText().trim();
        
        if (adminId.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both Admin ID and Password");
            statusLabel.setStyle("-fx-text-fill: #d32f2f;");
            return;
        }
        
        // Check admin credentials (hardcoded: admin/007)
        if (adminId.equals("admin") && password.equals("007")) {
            navigateToAdminDashboard(adminId);
        } else {
            statusLabel.setText("Invalid admin credentials");
            statusLabel.setStyle("-fx-text-fill: #d32f2f;");
        }
    }

    @FXML
    protected void onBackClick() throws IOException {
        // Load the welcome view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/welcome-view.fxml"));
        Parent welcomeRoot = fxmlLoader.load();
        
        // Get the current stage
        Stage stage = (Stage) backButton.getScene().getWindow();
        
        // Set the new scene
        Scene welcomeScene = new Scene(welcomeRoot, 1000, 700);
        stage.setScene(welcomeScene);
        stage.setTitle("Flight Booking - Welcome");
    }
    
    private void navigateToUserDashboard(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/user-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        UserDashboardController controller = fxmlLoader.getController();
        controller.setUsername(username);
        
        Stage stage = (Stage) userIdField.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("User Dashboard - Flight Booking");
    }
    
    private void navigateToAdminDashboard(String username) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/admin-home-view.fxml"));
        Parent root = fxmlLoader.load();
        AdminHomeController controller = fxmlLoader.getController();
        controller.setUsername(username);
        
        Stage stage = (Stage) adminIdField.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard - Flight Booking");
    }
}
