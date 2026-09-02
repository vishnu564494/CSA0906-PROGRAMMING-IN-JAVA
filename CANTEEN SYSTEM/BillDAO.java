import java.sql.*;

public class BillDAO {

    // INSERT BILL
    public void saveBill(Bill bill) {

        String sql = "INSERT INTO bills " +
                     "(order_id, subtotal, tax, total_amount, payment_status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bill.getOrder().getOrderId());
            ps.setDouble(2, bill.getSubtotal());
            ps.setDouble(3, bill.getTax());
            ps.setDouble(4, bill.getTotalAmount());
            ps.setString(5, bill.getPaymentStatus());

            ps.executeUpdate();

            System.out.println("Bill saved successfully.");

        } catch (SQLException e) {

            System.out.println(
                    "Error saving bill: " + e.getMessage()
            );
        }
    }

    // UPDATE PAYMENT STATUS
    public void updatePaymentStatus(
            int billId,
            String status) {

        String sql =
                "UPDATE bills SET payment_status=? " +
                "WHERE bill_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, billId);

            ps.executeUpdate();

            System.out.println(
                    "Payment status updated successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error updating payment: " +
                    e.getMessage()
            );
        }
    }

    // DISPLAY ALL BILLS
    public void displayAllBills() {

        String sql = "SELECT * FROM bills";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println(
                    "\n========== ALL BILLS =========="
            );

            while (rs.next()) {

                System.out.println(
                        "Bill ID : " +
                        rs.getInt("bill_id")
                );

                System.out.println(
                        "Order ID : " +
                        rs.getInt("order_id")
                );

                System.out.printf(
                        "Subtotal : Rs. %.2f%n",
                        rs.getDouble("subtotal")
                );

                System.out.printf(
                        "Tax : Rs. %.2f%n",
                        rs.getDouble("tax")
                );

                System.out.printf(
                        "Total : Rs. %.2f%n",
                        rs.getDouble("total_amount")
                );

                System.out.println(
                        "Payment : " +
                        rs.getString("payment_status")
                );

                System.out.println(
                        "--------------------------------"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving bills: " +
                    e.getMessage()
            );
        }
    }
}