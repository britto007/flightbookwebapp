# Flight Booking Application

A comprehensive JavaFX-based flight booking system with SQLite database integration, featuring separate user and admin interfaces for managing flights, bookings, and users.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [Installation & Setup](#installation--setup)
- [Usage Guide](#usage-guide)
- [Implementation Details](#implementation-details)
- [Project Structure](#project-structure)

---

## Overview

This Flight Booking Application is a desktop application built with JavaFX that provides a complete flight booking management system. The application supports two types of users:

- **Regular Users**: Can search for flights, make bookings, and manage their bookings
- **Administrators**: Can add/delete flights, view all bookings, and manage user accounts

The application uses SQLite as the database backend, providing persistent storage for all flight, booking, and user data.

---

## Features

### User Features

1. **User Registration & Login**
   - Automatic account creation for new users
   - Username-based authentication
   - Password-protected login

2. **Flight Search**
   - Search flights by origin and destination cities
   - Case-insensitive search with partial matching support
   - Filter by departure date
   - Select flight class (Economy, Economy Plus, Business, First)
   - Configure number of adults and children

3. **Flight Booking**
   - View search results with flight details
   - Book selected flights with passenger information
   - Automatic booking ID generation
   - Booking confirmation

4. **My Bookings**
   - View all personal bookings
   - Display booking details (ID, flight number, route, date, status)
   - Delete bookings with confirmation

### Admin Features

1. **Admin Login**
   - Secure admin authentication (username: `admin`, password: `007`)

2. **Flight Management**
   - **Add Flights**: Create new flight entries with:
     - Flight number
     - Origin and destination cities
     - Departure and arrival dates/times
     - Price
     - Aircraft type
   - **Delete Flights**: Remove flights from the system

3. **Booking Management**
   - View all bookings across all users
   - See complete booking details

4. **User Management**
   - View all registered users
   - See user details (username, email, role, status, join date)

---

## Technology Stack

- **Java**: 21
- **JavaFX**: 21.0.6 (UI framework)
- **SQLite**: 3.44.1.0 (Database)
- **Maven**: Build and dependency management
- **FXML**: UI layout definition

### Key Dependencies

```xml
- JavaFX Controls, FXML, Web, Swing, Media
- SQLite JDBC Driver (org.xerial:sqlite-jdbc:3.44.1.0)
- ControlsFX (UI enhancements)
- FormsFX (Form components)
- ValidatorFX (Input validation)
```

---

## Architecture

### Design Pattern: Model-View-Controller (MVC)

The application follows the MVC pattern:

- **Model**: Data classes (`UserItem`, `FlightItem`, `BookingItem`) and `DatabaseHelper`
- **View**: FXML files defining the UI layout
- **Controller**: Java controller classes handling user interactions and business logic

### Key Components

1. **DatabaseHelper** (Singleton Pattern)
   - Centralized database access
   - Ensures single database connection instance
   - Provides CRUD operations for all entities

2. **Controllers**
   - Each screen has a dedicated controller
   - Handle UI events and navigation
   - Interact with `DatabaseHelper` for data operations

3. **Data Models**
   - `UserItem`: User information
   - `FlightItem`: Flight details
   - `BookingItem`: Booking information

---

## Database Schema

The application uses SQLite with three main tables:

### 1. Users Table

Stores user account information.

```sql
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    key TEXT UNIQUE,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    email TEXT,
    role TEXT NOT NULL,
    status TEXT NOT NULL,
    joinDate TEXT
)
```

**Columns:**
- `id`: Auto-incrementing primary key
- `key`: Unique identifier (format: `user_<timestamp>`)
- `username`: Unique username for login
- `password`: User password (stored in plain text - not recommended for production)
- `email`: User email address
- `role`: User role (typically "User" or "Admin")
- `status`: Account status (e.g., "Active")
- `joinDate`: Date when user joined (format: "d MMM, yyyy")

### 2. Flights Table

Stores flight information.

```sql
CREATE TABLE IF NOT EXISTS flights (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    key TEXT UNIQUE,
    flightNumber TEXT NOT NULL,
    from_city TEXT NOT NULL,
    to_city TEXT NOT NULL,
    departureDate TEXT NOT NULL,
    departureTime TEXT NOT NULL,
    arrivalDate TEXT,
    arrivalTime TEXT,
    price TEXT,
    aircraftType TEXT
)
```

**Columns:**
- `id`: Auto-incrementing primary key
- `key`: Unique identifier (format: `flight_<timestamp>`)
- `flightNumber`: Flight number (e.g., "AA123")
- `from_city`: Origin city
- `to_city`: Destination city
- `departureDate`: Departure date (format: "d MMM, yyyy")
- `departureTime`: Departure time (format: "HH:mm")
- `arrivalDate`: Arrival date (format: "d MMM, yyyy")
- `arrivalTime`: Arrival time (format: "HH:mm")
- `price`: Flight price
- `aircraftType`: Type of aircraft (e.g., "Boeing 737")

### 3. Bookings Table

Stores booking information.

```sql
CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    key TEXT UNIQUE,
    bookingId TEXT UNIQUE NOT NULL,
    passengerName TEXT NOT NULL,
    flightNumber TEXT NOT NULL,
    route TEXT NOT NULL,
    date TEXT NOT NULL,
    status TEXT NOT NULL,
    seatClass TEXT,
    phone TEXT,
    adults INTEGER DEFAULT 0,
    children INTEGER DEFAULT 0
)
```

**Columns:**
- `id`: Auto-incrementing primary key
- `key`: Unique identifier (format: `booking_<timestamp>`)
- `bookingId`: Unique booking ID
- `passengerName`: Name of the passenger (username)
- `flightNumber`: Associated flight number
- `route`: Flight route (format: "Origin → Destination")
- `date`: Booking date (format: "d MMM, yyyy")
- `status`: Booking status (e.g., "Confirmed")
- `seatClass`: Selected seat class
- `phone`: Passenger phone number
- `adults`: Number of adult passengers
- `children`: Number of child passengers

---

## Installation & Setup

### Prerequisites

- Java 21 or higher
- Maven 3.6+ (or use included Maven Wrapper)
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Steps

1. **Clone or Download the Project**
   ```bash
   cd flightbook
   ```

2. **Build the Project**
   ```bash
   # Using Maven Wrapper (Windows)
   .\mvnw.cmd clean compile
   
   # Using Maven Wrapper (Linux/Mac)
   ./mvnw clean compile
   
   # Or using installed Maven
   mvn clean compile
   ```

3. **Run the Application**
   ```bash
   # Using Maven Wrapper
   .\mvnw.cmd javafx:run
   
   # Or using installed Maven
   mvn javafx:run
   ```

4. **Database Setup**
   - The database (`flightbooking.db`) is automatically created on first run
   - Tables are initialized automatically by `DatabaseHelper`
   - No manual database setup required

---

## Usage Guide

### For Users

1. **Getting Started**
   - Launch the application
   - Click "Get Started" on the welcome screen

2. **Login/Create Account**
   - Enter a username and password in the "Traveler login" section
   - If the username doesn't exist, an account will be created automatically
   - If the username exists, enter the correct password

3. **Search Flights**
   - From the User Dashboard, click "Book Flight"
   - Enter origin and destination cities
   - Select departure date (and optionally return date)
   - Choose flight class
   - Adjust number of adults and children
   - Click "Search Flights"

4. **Book a Flight**
   - Review search results
   - Click "Book" on a desired flight
   - Enter passenger details (name, phone)
   - Confirm booking

5. **View Bookings**
   - From User Dashboard, click "My Bookings"
   - View all your bookings with details

6. **Delete Booking**
   - From User Dashboard, click "Delete Booking"
   - Select a booking and click "Delete"
   - Confirm deletion

### For Administrators

1. **Admin Login**
   - Enter username: `admin`
   - Enter password: `007`
   - Click "Admin login"

2. **Add Flight**
   - From Admin Dashboard, click "Add Flight"
   - Fill in all flight details:
     - Flight number
     - Origin and destination
     - Departure/arrival dates and times
     - Price
     - Aircraft type
   - Click "Submit"

3. **Delete Flight**
   - From Admin Dashboard, click "Delete Flight"
   - Select a flight from the list
   - Click "Delete"
   - Confirm deletion

4. **View All Bookings**
   - From Admin Dashboard, click "View Bookings"
   - See all bookings across all users

5. **Manage Users**
   - From Admin Dashboard, click "Manage Users"
   - View all registered users and their details

---

## Implementation Details

### Database Operations

All database operations are handled by the `DatabaseHelper` singleton class:

#### User Operations

- **`getUserByUsername(String username)`**: Retrieves a user by username
  ```java
  String sql = "SELECT * FROM users WHERE username = ?";
  ```
  - Uses `PreparedStatement` for parameterized queries
  - Returns `UserItem` object or `null` if not found

- **`createUser(String username, String password)`**: Creates a new user
  ```java
  String key = "user_" + System.currentTimeMillis();
  String email = username.contains("@") ? username : username + "@flightbooking.com";
  ```
  - Auto-generates unique key and email
  - Sets default role as "User" and status as "Active"
  - Returns `true` on success, `false` if user already exists

- **`getAllUsers()`**: Retrieves all users
  - Returns `List<UserItem>` of all registered users

#### Flight Operations

- **`addFlight(FlightItem flight)`**: Adds a new flight
  ```java
  String key = "flight_" + System.currentTimeMillis();
  ```
  - Generates unique key with timestamp
  - Inserts flight data into `flights` table
  - Logs success message

- **`getAllFlights()`**: Retrieves all flights
  - Returns `List<FlightItem>` of all flights
  - Used for flight search functionality

- **`deleteFlight(String key)`**: Deletes a flight by key
  ```java
  String sql = "DELETE FROM flights WHERE key = ?";
  ```

#### Booking Operations

- **`addBooking(BookingItem booking)`**: Creates a new booking
  ```java
  String key = "booking_" + System.currentTimeMillis();
  booking.setKey(key);
  ```
  - Generates unique booking key
  - Stores passenger information and flight details
  - Supports multiple passengers (adults and children)

- **`getAllBookings()`**: Retrieves all bookings (admin view)
  - Returns `List<BookingItem>` of all bookings

- **`getBookingsByPassengerName(String passengerName)`**: Gets user's bookings
  ```java
  String sql = "SELECT * FROM bookings WHERE passengerName = ?";
  ```
  - Filters bookings by passenger name (username)
  - Used in "My Bookings" feature

- **`deleteBooking(String key)`**: Deletes a booking
  - Removes booking from database by unique key

### Flight Search Implementation

The flight search feature implements case-insensitive matching with partial support:

```java
String normalizedSearchFrom = searchFrom.trim().toLowerCase();
String normalizedSearchTo = searchTo.trim().toLowerCase();

for (FlightItem flight : allFlights) {
    String flightFrom = flight.getFrom().trim().toLowerCase();
    String flightTo = flight.getTo().trim().toLowerCase();
    
    boolean fromMatches = flightFrom.equals(normalizedSearchFrom) ||
                         flightFrom.contains(normalizedSearchFrom) ||
                         normalizedSearchFrom.contains(flightFrom);
    boolean toMatches = flightTo.equals(normalizedSearchTo) ||
                       flightTo.contains(normalizedSearchTo) ||
                       normalizedSearchTo.contains(flightTo);
    
    if (fromMatches && toMatches) {
        matchingFlights.add(flight);
    }
}
```

**Features:**
- Case-insensitive comparison
- Partial matching (e.g., "New York" matches "new york, usa")
- Bidirectional matching (search term can contain city or vice versa)

### Navigation Flow

The application uses JavaFX `FXMLLoader` for screen navigation:

```java
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/flightbook/login-view.fxml"));
Parent root = fxmlLoader.load();
Stage stage = (Stage) currentButton.getScene().getWindow();
Scene scene = new Scene(root, 1000, 700);
stage.setScene(scene);
```

**Screen Flow:**
1. Welcome → Login
2. Login → User Dashboard (for users) or Admin Dashboard (for admins)
3. User Dashboard → Book Flight / My Bookings / Delete Booking
4. Book Flight → Search Results → Booking Confirmation
5. Admin Dashboard → Add Flight / Delete Flight / View Bookings / Manage Users

### Data Models

#### UserItem
```java
- key: String
- username: String
- password: String
- email: String
- role: String
- status: String
- joinDate: String
```

#### FlightItem
```java
- key: String
- flightNumber: String
- from: String (origin city)
- to: String (destination city)
- departureDate: String
- departureTime: String
- arrivalDate: String
- arrivalTime: String
- price: String
- aircraftType: String
```

#### BookingItem
```java
- key: String
- bookingId: String
- passengerName: String
- flightNumber: String
- route: String
- date: String
- status: String
- seatClass: String
- phone: String
- adults: int
- children: int
```

---

## Project Structure

```
flightbook/
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java          # Java module configuration
│       │   └── com/example/flightbook/
│       │       ├── HelloApplication.java  # Application entry point
│       │       ├── Launcher.java         # Launcher class
│       │       ├── DatabaseHelper.java    # Database operations (Singleton)
│       │       ├── Models/
│       │       │   ├── UserItem.java     # User data model
│       │       │   ├── FlightItem.java   # Flight data model
│       │       │   └── BookingItem.java  # Booking data model
│       │       └── Controllers/
│       │           ├── WelcomeController.java
│       │           ├── LoginController.java
│       │           ├── UserDashboardController.java
│       │           ├── UserHomeController.java
│       │           ├── FlightSearchResultsController.java
│       │           ├── MyBookingsController.java
│       │           ├── AdminHomeController.java
│       │           ├── AddFlightController.java
│       │           ├── DeleteFlightController.java
│       │           ├── ViewBookingsController.java
│       │           └── ManageUsersController.java
│       └── resources/
│           └── com/example/flightbook/
│               ├── welcome-view.fxml
│               ├── login-view.fxml
│               ├── user-dashboard-view.fxml
│               ├── user-home-view.fxml
│               ├── flight-search-results-view.fxml
│               ├── my-bookings-view.fxml
│               ├── admin-home-view.fxml
│               ├── add-flight-view.fxml
│               ├── delete-flight-view.fxml
│               ├── view-bookings-view.fxml
│               └── manage-users-view.fxml
├── target/                                # Compiled classes
├── flightbooking.db                       # SQLite database file
├── pom.xml                                # Maven configuration
├── mvnw                                   # Maven wrapper (Unix)
└── mvnw.cmd                               # Maven wrapper (Windows)
```

---

## Database Connection

The application uses SQLite JDBC driver for database connectivity:

```java
private static final String DB_URL = "jdbc:sqlite:flightbooking.db";
```

**Connection Management:**
- Uses try-with-resources for automatic connection closing
- Prepared statements for parameterized queries (prevents SQL injection)
- Singleton pattern ensures single database instance

**Example Connection:**
```java
try (Connection conn = DriverManager.getConnection(DB_URL);
     PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setString(1, parameter);
    ResultSet rs = pstmt.executeQuery();
    // Process results
} catch (SQLException e) {
    System.err.println("Error: " + e.getMessage());
}
```

---

## Security Considerations

⚠️ **Note**: This is a demonstration application. For production use, consider:

1. **Password Storage**: Currently passwords are stored in plain text. Use password hashing (bcrypt, Argon2) in production.
2. **SQL Injection**: The application uses `PreparedStatement` which prevents SQL injection, but always validate user inputs.
3. **Input Validation**: Add comprehensive input validation for all user inputs.
4. **Session Management**: Implement proper session management for user authentication.
5. **Error Handling**: Enhance error handling to avoid exposing sensitive information.

---

## Troubleshooting

### Common Issues

1. **Database not found**
   - The database is created automatically on first run
   - Ensure the application has write permissions in the project directory

2. **Compilation errors**
   - Ensure Java 21 is installed and configured
   - Run `mvn clean compile` to rebuild
   - In IntelliJ IDEA: File → Invalidate Caches → Invalidate and Restart

3. **FXML loading errors**
   - Ensure FXML files are in `src/main/resources/com/example/flightbook/`
   - Check that controller class names match in FXML files

4. **Module errors**
   - Verify `module-info.java` includes all required modules
   - Ensure package names match between classes and FXML controllers

---

## Future Enhancements

Potential improvements for the application:

1. **Enhanced Security**
   - Password hashing and encryption
   - Session management
   - Role-based access control

2. **Additional Features**
   - Flight seat selection
   - Payment integration
   - Email notifications
   - Flight cancellation and refunds
   - User profile management
   - Flight history and analytics

3. **UI/UX Improvements**
   - Responsive design
   - Dark mode
   - Better error messages
   - Loading indicators

4. **Database Improvements**
   - Database migrations
   - Connection pooling
   - Transaction management
   - Data backup and restore

---

## License

This project is provided as-is for educational and demonstration purposes.

---

## Contact & Support

For issues or questions, please refer to the project documentation or create an issue in the project repository.

---

**Last Updated**: 2024
**Version**: 1.0-SNAPSHOT
