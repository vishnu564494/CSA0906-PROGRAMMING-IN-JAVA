import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> typeBox;

    public RegisterFrame() {

        setTitle("Customer Registration");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel typeLabel = new JLabel("Customer Type:");

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        typeBox = new JComboBox<>(
                new String[]{"Student", "Faculty", "Staff"}
        );

        JButton registerButton =
                new JButton("Register");

        JButton clearButton =
                new JButton("Clear");

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(typeLabel);
        panel.add(typeBox);

        panel.add(registerButton);
        panel.add(clearButton);

        add(panel);

        registerButton.addActionListener(e -> register());

        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void register() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        String type =
                typeBox.getSelectedItem().toString();

        if (name.isEmpty()
                || email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        Customer customer;

        if (type.equals("Student")) {

            customer = new Student(
                    0, name, email, password
            );

        } else if (type.equals("Faculty")) {

            customer = new Faculty(
                    0, name, email, password
            );

        } else {

            customer = new Staff(
                    0, name, email, password
            );
        }

        CustomerDAO dao =
                new CustomerDAO();

        if (dao.registerCustomer(customer)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!"
            );

            clearFields();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Failed!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearFields() {

        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        typeBox.setSelectedIndex(0);
    }
}