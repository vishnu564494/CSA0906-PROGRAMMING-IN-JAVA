import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {

            String url = "jdbc:mysql://localhost:3306/employee_db";
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(
                    url, user, password);
        }

        return connection;
    }
}