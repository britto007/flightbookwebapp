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

public class AddFlightController {
    @FXML
    private TextField flightNumberField;
    
    @FXML
    private TextField fromField;
    
    @FXML
    private TextField toField;
    
    @FXML
    private DatePicker departureDatePicker;
    
    @FXML
    private TextField departureTimeField;
    
    @FXML
    private DatePicker arrivalDatePicker;
    
    @FXML
    private TextField arrivalTimeField;
    
    @FXML
    private TextField priceField;
    
    @FXML
    private ComboBox<String> aircraftComboBox;
    
    @FXML
    private Button backButton;
    
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance();
    
    private static final String[] CITIES = {
        "New York, USA", "Los Angeles, USA", "Chicago, USA", "Miami, USA", "San Francisco, USA",
        "London, UK", "Paris, France", "Tokyo, Japan", "Dubai, UAE", "Singapore",
        "Sydney, Australia", "Toronto, Canada", "Berlin, Germany", "Rome, Italy", "Barcelona, Spain",
        "Amsterdam, Netherlands", "Hong Kong", "Bangkok, Thailand", "Mumbai, India", "Seoul, South Korea"
    };
    
    private static final String[] AIRCRAFT_TYPES = {
        "Boeing 737", "Boeing 777", "Boeing 787", "Airbus A320", "Airbus A330", "Airbus A350"
    };
    
    public void initialize() {
        departureDatePicker.setValue(LocalDate.now());
        arrivalDatePicker.setValue(LocalDate.now().plusDays(1));
        departureTimeField.setText("10:00");
        arrivalTimeField.setText("12:00");
        
        aircraftComboBox.getItems().addAll(AIRCRAFT_TYPES);
        aircraftComboBox.setValue("Boeing 737");
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
    
    @FXML
    protected void onSubmitClick() {
        String flightNumber = flightNumberField.getText().trim();
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        LocalDate departureDate = departureDatePicker.getValue();
        String departureTime = departureTimeField.getText().trim();
        LocalDate arrivalDate = arrivalDatePicker.getValue();
        String arrivalTime = arrivalTimeField.getText().trim();
        String price = priceField.getText().trim();
        String aircraftType = aircraftComboBox.getValue();
        
        if (flightNumber.isEmpty() || from.isEmpty() || to.isEmpty() || 
            departureDate == null || departureTime.isEmpty() ||
            arrivalDate == null || arrivalTime.isEmpty() || 
            price.isEmpty() || aircraftType == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
            return;
        }
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM, yyyy");
        String departureDateStr = departureDate.format(dateFormatter);
        String arrivalDateStr = arrivalDate.format(dateFormatter);
        
        FlightItem flight = new FlightItem(flightNumber, from, to, departureDateStr, 
                                          departureTime, arrivalDateStr, arrivalTime, 
                                          price, aircraftType);
        
        dbHelper.addFlight(flight);
        
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Flight added successfully!");
        successAlert.showAndWait();
        
        try {
            onBackClick();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
