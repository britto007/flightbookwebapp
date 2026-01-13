package com.example.flightbook;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UserHomeController {
    @FXML
    private Label userNameLabel;
    
    @FXML
    private TextField fromField;
    
    @FXML
    private TextField toField;
    
    @FXML
    private DatePicker departureDatePicker;
    
    @FXML
    private DatePicker returnDatePicker;
    
    @FXML
    private ComboBox<String> classComboBox;
    
    @FXML
    private Label adultCountLabel;
    
    @FXML
    private Label childCountLabel;
    
    @FXML
    private Button backButton;
    
    private String username;
    private int adultCount = 2;
    private int childCount = 1;
    
    private static final String[] CITIES = {
        "New York, USA", "Los Angeles, USA", "Chicago, USA", "Miami, USA", "San Francisco, USA",
        "London, UK", "Paris, France", "Tokyo, Japan", "Dubai, UAE", "Singapore",
        "Sydney, Australia", "Toronto, Canada", "Berlin, Germany", "Rome, Italy", "Barcelona, Spain",
        "Amsterdam, Netherlands", "Hong Kong", "Bangkok, Thailand", "Mumbai, India", "Seoul, South Korea"
    };
    
    private static final String[] FLIGHT_CLASSES = {
        "Economy", "Economy Plus", "Business class", "First class"
    };
    
    public void initialize() {
        // Setup date pickers
        departureDatePicker.setValue(LocalDate.now());
        returnDatePicker.setValue(LocalDate.now().plusDays(5));
        departureDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        returnDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(departureDatePicker.getValue() != null ? departureDatePicker.getValue() : LocalDate.now()));
            }
        });
        
        // Setup class combo box
        classComboBox.getItems().addAll(FLIGHT_CLASSES);
        classComboBox.setValue("Business class");
        
        // Setup passenger counts
        updatePassengerLabels();
    }
    
    public void setUsername(String username) {
        this.username = username;
        if (userNameLabel != null) {
            userNameLabel.setText(username);
        }
    }
    
    @FXML
    protected void onBackClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/user-dashboard-view.fxml"));
        Parent root = fxmlLoader.load();
        UserDashboardController controller = fxmlLoader.getController();
        controller.setUsername(username);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("User Dashboard - Flight Booking");
    }
    
    @FXML
    protected void onAdultPlus() {
        adultCount++;
        updatePassengerLabels();
    }
    
    @FXML
    protected void onAdultMinus() {
        if (adultCount > 1) {
            adultCount--;
            updatePassengerLabels();
        }
    }
    
    @FXML
    protected void onChildPlus() {
        childCount++;
        updatePassengerLabels();
    }
    
    @FXML
    protected void onChildMinus() {
        if (childCount > 0) {
            childCount--;
            updatePassengerLabels();
        }
    }
    
    private void updatePassengerLabels() {
        adultCountLabel.setText(adultCount + " Adult");
        childCountLabel.setText(childCount + " Child");
    }
    
    @FXML
    protected void onSearchClick() throws IOException {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        LocalDate departureDate = departureDatePicker.getValue();
        String flightClass = classComboBox.getValue();
        
        if (from.isEmpty() || to.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
            return;
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM, yyyy");
        String dateStr = departureDate != null ? departureDate.format(formatter) : "";
        
        // Navigate to search results
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/flight-search-results-view.fxml"));
        Parent root = fxmlLoader.load();
        FlightSearchResultsController controller = fxmlLoader.getController();
        controller.setSearchParams(from, to, dateStr, flightClass, username, adultCount, childCount);
        
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Search Results - Flight Booking");
    }
}
