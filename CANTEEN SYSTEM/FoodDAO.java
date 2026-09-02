import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    // INSERT
    public void addFood(Food food) {

        String sql = "INSERT INTO food " +
                     "(food_name, category, price, stock) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, food.getFoodName());
            ps.setString(2, food.getCategory());
            ps.setDouble(3, food.getPrice());
            ps.setInt(4, food.getStock());

            ps.executeUpdate();

            System.out.println("Food added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding food: " + e.getMessage());
        }
    }

    // SELECT
    public List<Food> getAllFood() {

        List<Food> foodList = new ArrayList<>();

        String sql = "SELECT * FROM food";

        try (Connection con = DatabaseConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Food food = new Food(
                        rs.getInt("food_id"),
                        rs.getString("food_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );

                foodList.add(food);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving food: "
                    + e.getMessage());
        }

        return foodList;
    }

    // UPDATE
    public void updateFood(Food food) {

        String sql = "UPDATE food SET " +
                     "food_name=?, category=?, price=?, stock=? " +
                     "WHERE food_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, food.getFoodName());
            ps.setString(2, food.getCategory());
            ps.setDouble(3, food.getPrice());
            ps.setInt(4, food.getStock());
            ps.setInt(5, food.getFoodId());

            ps.executeUpdate();

            System.out.println("Food updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating food: "
                    + e.getMessage());
        }
    }

    // DELETE
    public void deleteFood(int foodId) {

        String sql = "DELETE FROM food WHERE food_id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, foodId);

            ps.executeUpdate();

            System.out.println("Food deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting food: "
                    + e.getMessage());
        }
    }
}