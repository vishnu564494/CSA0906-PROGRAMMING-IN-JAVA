import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class FlightBooking {

    static String url = "jdbc:mysql://localhost:3306/flightdb";
    static String username = "root";
    static String password = "root";


    // ================= VIEW FLIGHTS =================

    public static void viewFlights() {

        try {
            Connection con = DriverManager.getConnection(
                url, username, password
            );

            String sql =
                "SELECT * FROM flights WHERE available_seats > 0";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n===== AVAILABLE FLIGHTS =====");

            while (rs.next()) {

                System.out.println(
                    "Flight ID: " + rs.getInt("flight_id") +
                    " | Flight: " + rs.getString("flight_number") +
                    " | From: " + rs.getString("source") +
                    " | To: " + rs.getString("destination") +
                    " | Time: " + rs.getString("departure_time") +
                    " | Available Seats: " +
                    rs.getInt("available_seats")
                );
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Error while displaying flights."
            );

            e.printStackTrace();
        }
    }


    // ================= BOOK TICKET =================

    public static void bookTicket(Scanner sc) {

        System.out.println("\n===== BOOK TICKET =====");

        sc.nextLine();

        System.out.print("Enter passenger name: ");
        String passengerName = sc.nextLine();

        System.out.print("Enter flight ID: ");
        int flightId = sc.nextInt();

        System.out.print("Enter number of seats: ");
        int seats = sc.nextInt();

        if (seats <= 0) {
            System.out.println(
                "Number of seats must be greater than 0."
            );
            return;
        }

        try {

            Connection con = DriverManager.getConnection(
                url, username, password
            );

            String checkSql =
                "SELECT available_seats FROM flights " +
                "WHERE flight_id = ?";

            PreparedStatement checkStmt =
                con.prepareStatement(checkSql);

            checkStmt.setInt(1, flightId);

            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {

                System.out.println("Flight not found.");
                con.close();
                return;
            }

            int availableSeats =
                rs.getInt("available_seats");

            if (seats > availableSeats) {

                System.out.println(
                    "Not enough seats available."
                );

                System.out.println(
                    "Available seats: " + availableSeats
                );

                con.close();
                return;
            }


            // Insert booking
            String bookingSql =
                "INSERT INTO bookings " +
                "(passenger_name, flight_id, seats_booked, booking_status) " +
                "VALUES (?, ?, ?, 'CONFIRMED')";

            PreparedStatement bookingStmt =
                con.prepareStatement(
                    bookingSql,
                    Statement.RETURN_GENERATED_KEYS
                );

            bookingStmt.setString(1, passengerName);
            bookingStmt.setInt(2, flightId);
            bookingStmt.setInt(3, seats);

            bookingStmt.executeUpdate();


            // Get booking ID
            ResultSet generatedKeys =
                bookingStmt.getGeneratedKeys();

            int bookingId = 0;

            if (generatedKeys.next()) {
                bookingId = generatedKeys.getInt(1);
            }


            // Reduce available seats
            String updateSql =
                "UPDATE flights " +
                "SET available_seats = available_seats - ? " +
                "WHERE flight_id = ?";

            PreparedStatement updateStmt =
                con.prepareStatement(updateSql);

            updateStmt.setInt(1, seats);
            updateStmt.setInt(2, flightId);

            updateStmt.executeUpdate();


            System.out.println(
                "\n===== BOOKING SUCCESSFUL ====="
            );

            System.out.println(
                "Booking ID: " + bookingId
            );

            System.out.println(
                "Passenger: " + passengerName
            );

            System.out.println(
                "Flight ID: " + flightId
            );

            System.out.println(
                "Seats booked: " + seats
            );

            System.out.println(
                "Booking Status: CONFIRMED"
            );


            generatedKeys.close();
            bookingStmt.close();
            updateStmt.close();
            rs.close();
            checkStmt.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Booking failed."
            );

            e.printStackTrace();
        }
    }


    // ================= VIEW BOOKING =================

    public static void viewBooking(Scanner sc) {

        System.out.println("\n===== VIEW BOOKING =====");

        System.out.print("Enter booking ID: ");
        int bookingId = sc.nextInt();

        try {

            Connection con = DriverManager.getConnection(
                url, username, password
            );

            String sql =
                "SELECT b.booking_id, b.passenger_name, " +
                "f.flight_number, f.source, f.destination, " +
                "b.seats_booked, b.booking_status " +
                "FROM bookings b " +
                "JOIN flights f ON b.flight_id = f.flight_id " +
                "WHERE b.booking_id = ?";

            PreparedStatement stmt =
                con.prepareStatement(sql);

            stmt.setInt(1, bookingId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                System.out.println(
                    "\n===== BOOKING DETAILS ====="
                );

                System.out.println(
                    "Booking ID: " +
                    rs.getInt("booking_id")
                );

                System.out.println(
                    "Passenger: " +
                    rs.getString("passenger_name")
                );

                System.out.println(
                    "Flight: " +
                    rs.getString("flight_number")
                );

                System.out.println(
                    "From: " +
                    rs.getString("source")
                );

                System.out.println(
                    "To: " +
                    rs.getString("destination")
                );

                System.out.println(
                    "Seats Booked: " +
                    rs.getInt("seats_booked")
                );

                System.out.println(
                    "Status: " +
                    rs.getString("booking_status")
                );

            } else {

                System.out.println(
                    "Booking not found."
                );
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Error while viewing booking."
            );

            e.printStackTrace();
        }
    }


    // ================= CANCEL BOOKING =================

    public static void cancelBooking(Scanner sc) {

        System.out.println("\n===== CANCEL BOOKING =====");

        System.out.print("Enter booking ID: ");
        int bookingId = sc.nextInt();

        try {

            Connection con = DriverManager.getConnection(
                url, username, password
            );


            // Get booking information
            String checkSql =
                "SELECT flight_id, seats_booked, booking_status " +
                "FROM bookings WHERE booking_id = ?";

            PreparedStatement checkStmt =
                con.prepareStatement(checkSql);

            checkStmt.setInt(1, bookingId);

            ResultSet rs = checkStmt.executeQuery();


            // Check booking exists
            if (!rs.next()) {

                System.out.println(
                    "Booking not found."
                );

                con.close();
                return;
            }


            int flightId = rs.getInt("flight_id");

            int seatsBooked =
                rs.getInt("seats_booked");

            String status =
                rs.getString("booking_status");


            // Check already cancelled
            if (status.equals("CANCELLED")) {

                System.out.println(
                    "Booking is already cancelled."
                );

                con.close();
                return;
            }


            // Update booking status
            String cancelSql =
                "UPDATE bookings " +
                "SET booking_status = 'CANCELLED' " +
                "WHERE booking_id = ?";

            PreparedStatement cancelStmt =
                con.prepareStatement(cancelSql);

            cancelStmt.setInt(1, bookingId);

            cancelStmt.executeUpdate();


            // Return seats to flight
            String returnSeatsSql =
                "UPDATE flights " +
                "SET available_seats = available_seats + ? " +
                "WHERE flight_id = ?";

            PreparedStatement returnStmt =
                con.prepareStatement(returnSeatsSql);

            returnStmt.setInt(1, seatsBooked);
            returnStmt.setInt(2, flightId);

            returnStmt.executeUpdate();


            System.out.println(
                "\n===== BOOKING CANCELLED ====="
            );

            System.out.println(
                "Booking ID: " + bookingId
            );

            System.out.println(
                "Seats returned: " + seatsBooked
            );

            System.out.println(
                "Booking Status: CANCELLED"
            );


            returnStmt.close();
            cancelStmt.close();
            rs.close();
            checkStmt.close();
            con.close();

        } catch (Exception e) {

            System.out.println(
                "Cancellation failed."
            );

            e.printStackTrace();
        }
    }


    // ================= MAIN MENU =================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println(
            "Database connected successfully!"
        );

        while (true) {

            System.out.println(
                "\n===== FLIGHT BOOKING SYSTEM ====="
            );

            System.out.println(
                "1. View Available Flights"
            );

            System.out.println(
                "2. Book Ticket"
            );

            System.out.println(
                "3. View Booking"
            );

            System.out.println(
                "4. Cancel Booking"
            );

            System.out.println(
                "5. Exit"
            );

            System.out.print(
                "Enter your choice: "
            );

            int choice = sc.nextInt();


            switch (choice) {

                case 1:

                    viewFlights();
                    break;


                case 2:

                    bookTicket(sc);
                    break;


                case 3:

                    viewBooking(sc);
                    break;


                case 4:

                    cancelBooking(sc);
                    break;


                case 5:

                    System.out.println(
                        "\nThank you!"
                    );

                    sc.close();
                    return;


                default:

                    System.out.println(
                        "\nInvalid choice!"
                    );
            }
        }
    }
}