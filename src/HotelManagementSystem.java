
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

public class HotelManagementSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "your password";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println();
                System.out.println("----------HOTEL MANAGEMENT SYSTEM----------");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("6. Exit");
                System.out.println("Choose an option: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, sc, statement);
                        break;
                    case 2:
                        viewReservations(connection, statement);
                        break;
                    case 3:
                        getRoomNumber(connection, sc, statement);
                        break;
                    case 4:
                        updateReservation(connection, sc, statement);
                        break;
                    case 5:
                        deleteReservation(connection, sc, statement);
                        break;
                    case 6:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice.Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner sc, Statement statement) {
        try {
            System.out.println("Enter guest name: ");
            String guestName = sc.next();
            sc.nextLine();

            System.out.println("Enter room number: ");
            int roomNumber = sc.nextInt();

            System.out.println("Enter contact number: ");
            String contactNumber = sc.next();

            String sql = "INSERT INTO reservations(guest_name,room_number,contact_number) " +
                    "VALUES('" + guestName + "'," + roomNumber + ", '" + contactNumber + "')";

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Reservation Successful!");
            } else {
                System.out.println("Reservation Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection, Statement statement) throws SQLException {
        String sql = "SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservations";

        ResultSet rs = statement.executeQuery(sql);

        System.out.println("Current Reservations:");
        System.out.println("+----------------+---------------+---------------+----------------+--------------------------------+");
        System.out.println("| Reservation ID | Guest         | Room Number   | Contact Number | Reservation Date               |");
        System.out.println("+----------------+---------------+---------------+----------------+--------------------------------+");

        while (rs.next()) {
            int reservationId = rs.getInt("reservation_id");
            String guestName = rs.getString("guest_name");
            int roomNumber = rs.getInt("room_number");
            String contactNumber = rs.getString("contact_number");
            String reservationDate = rs.getTimestamp("reservation_date").toString();

            System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                    reservationId, guestName, roomNumber, contactNumber, reservationDate);
        }

        System.out.println("+----------------+------------------+------------------+--------------------+-----------------------+");
    }

    private static void getRoomNumber(Connection connection, Scanner sc, Statement statement) {
        try {
            System.out.println("Enter reservation ID: ");
            int reservationId = sc.nextInt();

            System.out.println("Enter guest name: ");
            String guestName = sc.next();

            String sql = "SELECT room_number FROM reservations " +
                    "WHERE reservation_id = " + reservationId +
                    " AND guest_name = '" + guestName + "'";

            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                int roomNumber = rs.getInt("room_number");
                System.out.println("Room number for Reservation ID " + reservationId +
                        " and Guest " + guestName + " is " + roomNumber);
            } else {
                System.out.println("Reservation not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner sc, Statement statement) {
        try {
            System.out.println("Enter reservation ID to update: ");
            int reservationId = sc.nextInt();
            sc.nextLine();

            if (!reservationExists(connection, reservationId, statement)) {
                System.out.println("Reservation not found.");
                return;
            }

            System.out.println("Enter new guest name: ");
            String newGuestname = sc.nextLine();

            System.out.println("Enter new room number: ");
            int newRoomNumber = sc.nextInt();

            System.out.println("Enter new contact number: ");
            String newContactNumber = sc.next();


            String sql = "UPDATE reservations SET guest_name = '" + newGuestname + "', " +
                    "room_number = " + newRoomNumber + ", " +
                    "contact_number = '" + newContactNumber + "' " +
                    "WHERE reservation_id = " + reservationId;

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Reservation updated Successfully!");
            } else {
                System.out.println("Reservation update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc, Statement statement) {
        try {
            System.out.println("Enter Reservation ID to delete: ");
            int reservationId = sc.nextInt();

            if (!reservationExists(connection, reservationId, statement)) {
                System.out.println("Reservation not found.");
                return;
            }

            String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

            int affectedRows = statement.executeUpdate(sql);

            if (affectedRows > 0) {
                System.out.println("Reservation Deleted Successfully");
            } else {
                System.out.println("Reservation Deletion Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId, Statement statement) {
        try {
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationId;

            ResultSet rs = statement.executeQuery(sql);
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }
}

