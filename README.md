🏨 Hotel Management System (Console-Based)
A Java console-based application for managing hotel reservations using JDBC and MySQL.
This project demonstrates how to build a real-world system with database connectivity and perform complete CRUD operations.
🚀 Features
Reserve a room for guests
View all reservation records
Get room number using reservation ID and guest name
Update existing reservation details
Delete reservation records
Menu-driven interface for easy navigation
Displays data in a structured tabular format
Includes validation checks before update and delete operations
🛠️ Tech Stack
Java (Core Java)
JDBC (Java Database Connectivity)
MySQL
SQL
📂 Project Structure

HotelManagementSystem.java
⚙️ Setup Instructions
1. Clone the Repository
Bash
git clone https://github.com/khushiporia1211/hotel-management-system.git
cd hotel-management-system
2. Create Database
Run the following SQL commands:
SQL
CREATE DATABASE hotel_db;
USE hotel_db;

CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    guest_name VARCHAR(100),
    room_number INT,
    contact_number VARCHAR(15),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
3. Configure Database Connection
Update your MySQL credentials in the Java file:
Java
private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
private static final String username = "your_username";
private static final String password = "your_password";
4. Compile and Run
Bash
javac HotelManagementSystem.java
java HotelManagementSystem
📸 Sample Output

----------HOTEL MANAGEMENT SYSTEM----------
1. Reserve a room
2. View Reservations
3. Get Room Number
4. Update Reservations
5. Delete Reservations
6. Exit

📌 Concepts Used
Object-Oriented Programming (OOP)
JDBC Connectivity
SQL (CRUD Operations)
Exception Handling
Menu-driven application design

⚠️ Future Improvements
Use PreparedStatement to prevent SQL Injection
Add GUI using Java Swing or JavaFX
Implement user authentication (Admin/User roles)
Improve code structure using multiple classes

📬 Contact
Name: Khushi
GitHub: https://github.com/khushiporia1211⁠�
Email: khushiporia09@gmail.com
⭐ Support
If you found this project helpful, consider giving it a ⭐ on GitHub!
