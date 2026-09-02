import java.sql.*;

public class CustomerDAO {

    // REGISTER CUSTOMER
    public boolean registerCustomer(Customer customer) {

        String sql = "INSERT INTO customers " +
                     "(name, email, password, customer_type) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getCustomerType());

            ps.executeUpdate();

            System.out.println("Customer registered successfully.");
            return true;

        } catch (SQLException e) {

            System.out.println(
                    "Registration failed: " + e.getMessage()
            );
            return false;
        }
    }

    // LOGIN CUSTOMER
    public Customer login(String email, String password) {

        String sql = "SELECT * FROM customers " +
                     "WHERE email=? AND password=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("customer_type")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Login error: " + e.getMessage()
            );
        }

        return null;
    }

    // DELETE CUSTOMER
    public void deleteCustomer(int customerId) {

        String sql =
                "DELETE FROM customers WHERE customer_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            ps.executeUpdate();

            System.out.println("Customer deleted successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting customer: " +
                    e.getMessage()
            );
        }
    }
}