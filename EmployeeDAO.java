import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ADD EMPLOYEE
    public boolean addEmployee(
            int id,
            String name,
            String department,
            String designation,
            String date,
            double salary,
            String email,
            String phone,
            String status) throws SQLException {

        String sql = "INSERT INTO employee " +
                "(emp_id, emp_name, department, designation, " +
                "date_of_joining, salary, email, phone, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, department);
            ps.setString(4, designation);
            ps.setDate(5, Date.valueOf(date));
            ps.setDouble(6, salary);
            ps.setString(7, email);
            ps.setString(8, phone);
            ps.setString(9, status);

            return ps.executeUpdate() > 0;
        }
    }

    // GET ALL EMPLOYEES
    public List<Object[]> getAllEmployees() throws SQLException {

        List<Object[]> employees = new ArrayList<>();

        String sql = "SELECT * FROM employee ORDER BY emp_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                employees.add(new Object[]{
                    rs.getInt("emp_id"),
                    rs.getString("emp_name"),
                    rs.getString("department"),
                    rs.getString("designation"),
                    rs.getDate("date_of_joining"),
                    rs.getDouble("salary"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("status")
                });
            }
        }

        return employees;
    }

    // UPDATE EMPLOYEE
    public boolean updateEmployee(
            int id,
            String name,
            String department,
            String designation,
            String date,
            double salary,
            String email,
            String phone,
            String status) throws SQLException {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE employee SET " +
                "emp_name=?, department=?, designation=?, " +
                "date_of_joining=?, salary=?, email=?, " +
                "phone=?, status=? WHERE emp_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, designation);
            ps.setDate(4, Date.valueOf(date));
            ps.setDouble(5, salary);
            ps.setString(6, email);
            ps.setString(7, phone);
            ps.setString(8, status);
            ps.setInt(9, id);

            int result = ps.executeUpdate();

            con.commit();
            con.setAutoCommit(true);

            return result > 0;

        } catch (SQLException e) {

            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }

            throw e;
        }
    }

    // DELETE EMPLOYEE
    public boolean deleteEmployee(int id) throws SQLException {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM employee WHERE emp_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            ps.setInt(1, id);

            int result = ps.executeUpdate();

            con.commit();
            con.setAutoCommit(true);

            return result > 0;

        } catch (SQLException e) {

            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ignored) {
            }

            throw e;
        }
    }

    // SEARCH EMPLOYEE
    public List<Object[]> searchEmployees(
            String type,
            String value) throws SQLException {

        List<Object[]> employees = new ArrayList<>();

        String sql;

        if (type.equals("ID")) {

            sql = "SELECT * FROM employee " +
                  "WHERE CAST(emp_id AS CHAR) LIKE ?";

        } else if (type.equals("Name")) {

            sql = "SELECT * FROM employee " +
                  "WHERE emp_name LIKE ?";

        } else {

            sql = "SELECT * FROM employee " +
                  "WHERE department LIKE ?";
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + value + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    employees.add(new Object[]{
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getString("department"),
                        rs.getString("designation"),
                        rs.getDate("date_of_joining"),
                        rs.getDouble("salary"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("status")
                    });
                }
            }
        }

        return employees;
    }
}