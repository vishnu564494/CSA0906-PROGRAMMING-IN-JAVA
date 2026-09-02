import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/canteen_db";

    private static final String USER = "root";

    private static final String PASSWORD =
            "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {

        try {
            Connection connection = getConnection();

            System.out.println("=================================");
            System.out.println(" DATABASE CONNECTION SUCCESSFUL ");
            System.out.println("=================================");

            connection.close();

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}