import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Canteen Food Ordering System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel titleLabel =
                new JLabel("CANTEEN FOOD ORDERING SYSTEM",
                        SwingConstants.CENTER);

        JLabel emailLabel =
                new JLabel("Email:");

        JLabel passwordLabel =
                new JLabel("Password:");

        emailField = new JTextField();

        passwordField = new JPasswordField();

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Register");

        JButton adminButton =
                new JButton("Admin Login");

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(registerButton);

        panel.add(new JLabel(""));
        panel.add(adminButton);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // LOGIN BUTTON
        loginButton.addActionListener(e -> loginCustomer());

        // REGISTER BUTTON
        registerButton.addActionListener(e -> {

            new RegisterFrame();

        });

        // ADMIN BUTTON
        adminButton.addActionListener(e -> {

            String username =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Admin Username:"
                    );

            String password =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Admin Password:"
                    );

            if ("admin".equals(username)
                    && "admin123".equals(password)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admin Login Successful!"
                );

                new AdminFrame();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Admin Login!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }

    private void loginCustomer() {

        String email =
                emailField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email and password."
            );

            return;
        }

        CustomerDAO dao =
                new CustomerDAO();

        Customer customer =
                dao.login(email, password);

        if (customer != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!\nWelcome "
                    + customer.getName()
            );

            new CustomerFrame(customer);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}