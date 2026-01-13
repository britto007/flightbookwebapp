package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {
    @FXML
    private VBox usersContainer;
    
    @FXML
    private Button backButton;
    
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUsers();
    }
    
    private void loadUsers() {
        List<UserItem> users = dbHelper.getAllUsers();
        usersContainer.getChildren().clear();
        
        if (users.isEmpty()) {
            Label noUsersLabel = new Label("No users found");
            noUsersLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
            usersContainer.getChildren().add(noUsersLabel);
        } else {
            Label countLabel = new Label("Total Users: " + users.size());
            countLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
            usersContainer.getChildren().add(countLabel);
            
            for (UserItem user : users) {
                VBox userCard = createUserCard(user);
                usersContainer.getChildren().add(userCard);
            }
        }
    }
    
    private VBox createUserCard(UserItem user) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #34495E; -fx-background-radius: 12; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0, 0, 2); -fx-border-color: #16A085; -fx-border-width: 2; -fx-border-radius: 12;");
        
        Label usernameLabel = new Label("Username: " + user.getUsername());
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label emailLabel = new Label("Email: " + user.getEmail());
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label roleLabel = new Label("Role: " + user.getRole());
        roleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        Label statusLabel = new Label("Status: " + user.getStatus());
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #5DE1C0;");
        
        Label joinDateLabel = new Label("Join Date: " + user.getJoinDate());
        joinDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #E0E0E0;");
        
        card.getChildren().addAll(usernameLabel, emailLabel, roleLabel, statusLabel, joinDateLabel);
        return card;
    }
    
    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/admin-home-view.fxml"));
        Parent root = fxmlLoader.load();
        AdminHomeController controller = fxmlLoader.getController();
        controller.setUsername("admin");
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard - Flight Booking");
    }
}
