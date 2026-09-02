import java.sql.*;

public class OrderDAO {

    // INSERT ORDER AND ORDER ITEMS
    public int saveOrder(Order order) {

        String orderSQL =
                "INSERT INTO orders " +
                "(customer_id, total_amount, status) " +
                "VALUES (?, ?, ?)";

        String itemSQL =
                "INSERT INTO order_items " +
                "(order_id, food_id, quantity, price) " +
                "VALUES (?, ?, ?, ?)";

        Connection con = null;

        try {

            con = DatabaseConnection.getConnection();

            con.setAutoCommit(false);

            // Insert order
            PreparedStatement orderPS =
                    con.prepareStatement(
                            orderSQL,
                            Statement.RETURN_GENERATED_KEYS
                    );

            orderPS.setInt(
                    1,
                    order.getCustomer().getCustomerId()
            );

            orderPS.setDouble(
                    2,
                    order.getTotalAmount()
            );

            orderPS.setString(
                    3,
                    order.getStatus()
            );

            orderPS.executeUpdate();

            ResultSet keys =
                    orderPS.getGeneratedKeys();

            int orderId = 0;

            if (keys.next()) {
                orderId = keys.getInt(1);
            }

            // Insert order items
            PreparedStatement itemPS =
                    con.prepareStatement(itemSQL);

            for (int i = 0;
                 i < order.getFoodItems().size();
                 i++) {

                Food food =
                        order.getFoodItems().get(i);

                int quantity =
                        order.getQuantities().get(i);

                itemPS.setInt(1, orderId);
                itemPS.setInt(2, food.getFoodId());
                itemPS.setInt(3, quantity);
                itemPS.setDouble(4, food.getPrice());

                itemPS.executeUpdate();
            }

            con.commit();

            System.out.println(
                    "Order saved successfully."
            );

            return orderId;

        } catch (SQLException e) {

            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println(
                            "Rollback failed: " +
                            ex.getMessage()
                    );
                }
            }

            System.out.println(
                    "Error saving order: " +
                    e.getMessage()
            );

            return -1;

        } finally {

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(
                            "Connection close error."
                    );
                }
            }
        }
    }

    // UPDATE ORDER STATUS
    public void updateOrderStatus(
            int orderId,
            String status) {

        String sql =
                "UPDATE orders SET status=? " +
                "WHERE order_id=?";

        try (Connection con =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            ps.executeUpdate();

            System.out.println(
                    "Order status updated."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error updating order: " +
                    e.getMessage()
            );
        }
    }

    // DELETE / CANCEL ORDER
    public void cancelOrder(int orderId) {

        String sql =
                "UPDATE orders SET status='Cancelled' " +
                "WHERE order_id=?";

        try (Connection con =
                     DatabaseConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            ps.executeUpdate();

            System.out.println(
                    "Order cancelled successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error cancelling order: " +
                    e.getMessage()
            );
        }
    }

    // DISPLAY ALL ORDERS
    public void displayAllOrders() {

        String sql =
                "SELECT * FROM orders";

        try (Connection con =
                     DatabaseConnection.getConnection();
             Statement st =
                     con.createStatement();
             ResultSet rs =
                     st.executeQuery(sql)) {

            System.out.println(
                    "\n========== ALL ORDERS =========="
            );

            while (rs.next()) {

                System.out.println(
                        "Order ID : " +
                        rs.getInt("order_id")
                );

                System.out.println(
                        "Customer ID : " +
                        rs.getInt("customer_id")
                );

                System.out.println(
                        "Total : Rs." +
                        rs.getDouble("total_amount")
                );

                System.out.println(
                        "Status : " +
                        rs.getString("status")
                );

                System.out.println(
                        "--------------------------------"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving orders: " +
                    e.getMessage()
            );
        }
    }
}