import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class EmployeeGUI extends JFrame {

    JTextField txtId, txtName, txtDate, txtSalary, txtEmail, txtPhone;
    JTextField txtSearch;

    JComboBox<String> cmbDepartment;
    JComboBox<String> cmbDesignation;
    JComboBox<String> cmbSearchType;

    JRadioButton rdoActive, rdoInactive;

    JButton btnAdd, btnUpdate, btnDelete, btnClear;
    JButton btnSearch, btnRefresh;

    JTable tblEmployee;
    JLabel lblStatus, lblConnection;

    EmployeeDAO dao = new EmployeeDAO();

    public EmployeeGUI() {

        setTitle("Employee Information Management");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        checkConnection();
        loadEmployees();

        setVisible(true);
    }

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // ================= TITLE =================

        JLabel title = new JLabel(
                "EMPLOYEE INFORMATION MANAGEMENT",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));

        add(title, BorderLayout.NORTH);

        // ================= INPUT PANEL =================

        JPanel inputPanel = new JPanel(
                new GridBagLayout()
        );

        inputPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Employee Information"
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(15);
        txtName = new JTextField(15);
        txtDate = new JTextField(15);
        txtSalary = new JTextField(15);
        txtEmail = new JTextField(15);
        txtPhone = new JTextField(15);

        cmbDepartment = new JComboBox<>(
                new String[]{
                    "IT",
                    "HR",
                    "Finance",
                    "Sales",
                    "Marketing"
                }
        );

        cmbDesignation = new JComboBox<>(
                new String[]{
                    "Developer",
                    "Manager",
                    "HR Manager",
                    "Accountant",
                    "Sales Executive",
                    "Tester"
                }
        );

        rdoActive = new JRadioButton("Active");
        rdoInactive = new JRadioButton("Inactive");

        rdoActive.setSelected(true);

        ButtonGroup statusGroup = new ButtonGroup();

        statusGroup.add(rdoActive);
        statusGroup.add(rdoInactive);

        JPanel statusPanel = new JPanel();

        statusPanel.add(rdoActive);
        statusPanel.add(rdoInactive);

        // Row 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Employee ID:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtId, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Employee Name:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtName, gbc);

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Department:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(cmbDepartment, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Designation:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(cmbDesignation, gbc);

        // Row 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Date of Joining:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtDate, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Salary:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtSalary, gbc);

        // Row 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtEmail, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtPhone, gbc);

        // Row 4
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        inputPanel.add(statusPanel, gbc);

        gbc.gridwidth = 1;

        // ================= BUTTON PANEL =================

        JPanel buttonPanel = new JPanel();

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(
                new BorderLayout()
        );

        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ================= SEARCH PANEL =================

        JPanel searchPanel = new JPanel();

        searchPanel.setBorder(
                BorderFactory.createTitledBorder("Search")
        );

        txtSearch = new JTextField(20);

        cmbSearchType = new JComboBox<>(
                new String[]{
                    "ID",
                    "Name",
                    "Department"
                }
        );

        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);
        searchPanel.add(cmbSearchType);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        // ================= TABLE =================

        String[] columns = {
            "ID",
            "Name",
            "Department",
            "Designation",
            "Date",
            "Salary",
            "Email",
            "Phone",
            "Status"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {
                return false;
            }
        };

        tblEmployee = new JTable(model);

        tblEmployee.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(tblEmployee);

        JPanel centerPanel = new JPanel(
                new BorderLayout()
        );

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(centerPanel, BorderLayout.CENTER);

        // ================= STATUS BAR =================

        JPanel statusPanelBottom = new JPanel(
                new BorderLayout()
        );

        lblConnection =
                new JLabel("● Checking connection...");

        lblStatus =
                new JLabel("Ready");

        statusPanelBottom.add(
                lblConnection,
                BorderLayout.WEST
        );

        statusPanelBottom.add(
                lblStatus,
                BorderLayout.EAST
        );

        add(
                statusPanelBottom,
                BorderLayout.SOUTH
        );

        // ================= EVENTS =================

        btnAdd.addActionListener(
                e -> addEmployee()
        );

        btnUpdate.addActionListener(
                e -> updateEmployee()
        );

        btnDelete.addActionListener(
                e -> deleteEmployee()
        );

        btnClear.addActionListener(
                e -> clearFields()
        );

        btnSearch.addActionListener(
                e -> searchEmployees()
        );

        btnRefresh.addActionListener(
                e -> loadEmployees()
        );

        tblEmployee.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        selectEmployee();
                    }
                }
        );

        txtSearch.addKeyListener(
                new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {
                        searchEmployees();
                    }
                }
        );

        // ENTER = ADD
        txtPhone.addActionListener(
                e -> btnAdd.doClick()
        );

        // ESC = CLEAR
        getRootPane().registerKeyboardAction(
                e -> clearFields(),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }

    // ================= CONNECTION =================

    private void checkConnection() {

        try {

            DBConnection.getConnection();

            lblConnection.setText(
                    "● Database Connected"
            );

        } catch (SQLException e) {

            lblConnection.setText(
                    "● Connection Failed"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection failed."
            );
        }
    }

    // ================= LOAD =================

    private void loadEmployees() {

        try {

            List<Object[]> employees =
                    dao.getAllEmployees();

            DefaultTableModel model =
                    (DefaultTableModel)
                    tblEmployee.getModel();

            model.setRowCount(0);

            for (Object[] employee : employees) {
                model.addRow(employee);
            }

            lblStatus.setText(
                    employees.size() +
                    " records found"
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load records."
            );
        }
    }

    // ================= ADD =================

    private void addEmployee() {

        try {

            String idText = txtId.getText().trim();
            String name = txtName.getText().trim();
            String salaryText =
                    txtSalary.getText().trim();

            String email =
                    txtEmail.getText().trim();

            String phone =
                    txtPhone.getText().trim();

            String date =
                    txtDate.getText().trim();

            if (Validation.isEmpty(idText)
                    || Validation.isEmpty(name)
                    || Validation.isEmpty(salaryText)) {

                JOptionPane.showMessageDialog(
                        this,
                        "ID, Name and Salary are required."
                );

                return;
            }

            if (!Validation.isValidSalary(
                    salaryText)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Salary must be a valid number."
                );

                return;
            }

            if (!Validation.isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter a valid email."
                );

                return;
            }

            if (!Validation.isValidPhone(phone)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Phone must contain 10 digits."
                );

                return;
            }

            int id = Integer.parseInt(idText);

            double salary =
                    Double.parseDouble(salaryText);

            String department =
                    cmbDepartment
                    .getSelectedItem()
                    .toString();

            String designation =
                    cmbDesignation
                    .getSelectedItem()
                    .toString();

            String status =
                    rdoActive.isSelected()
                    ? "Active"
                    : "Inactive";

            dao.addEmployee(
                    id,
                    name,
                    department,
                    designation,
                    date,
                    salary,
                    email,
                    phone,
                    status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Record added successfully."
            );

            clearFields();
            loadEmployees();

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Employee ID already exists."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid information."
            );
        }
    }

    // ================= SELECT =================

    private void selectEmployee() {

        int row =
                tblEmployee.getSelectedRow();

        if (row == -1) {
            return;
        }

        int modelRow =
                tblEmployee.convertRowIndexToModel(row);

        txtId.setText(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 0)
                .toString()
        );

        txtName.setText(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 1)
                .toString()
        );

        cmbDepartment.setSelectedItem(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 2)
        );

        cmbDesignation.setSelectedItem(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 3)
        );

        txtDate.setText(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 4)
                .toString()
        );

        txtSalary.setText(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 5)
                .toString()
        );

        txtEmail.setText(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 6)
                .toString()
        );

        txtPhone.setText(
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 7)
                .toString()
        );

        String status =
                tblEmployee
                .getModel()
                .getValueAt(modelRow, 8)
                .toString();

        if (status.equals("Active")) {
            rdoActive.setSelected(true);
        } else {
            rdoInactive.setSelected(true);
        }

        txtId.setEditable(false);

        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
    }

    // ================= UPDATE =================

    private void updateEmployee() {

        try {

            int id =
                    Integer.parseInt(txtId.getText());

            String name =
                    txtName.getText().trim();

            String date =
                    txtDate.getText().trim();

            double salary =
                    Double.parseDouble(
                            txtSalary.getText()
                    );

            String email =
                    txtEmail.getText().trim();

            String phone =
                    txtPhone.getText().trim();

            if (Validation.isEmpty(name)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Name cannot be empty."
                );

                return;
            }

            if (!Validation.isValidSalary(
                    txtSalary.getText())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid salary."
                );

                return;
            }

            if (!Validation.isValidEmail(email)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid email."
                );

                return;
            }

            if (!Validation.isValidPhone(phone)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Phone must contain 10 digits."
                );

                return;
            }

            String department =
                    cmbDepartment
                    .getSelectedItem()
                    .toString();

            String designation =
                    cmbDesignation
                    .getSelectedItem()
                    .toString();

            String status =
                    rdoActive.isSelected()
                    ? "Active"
                    : "Inactive";

            dao.updateEmployee(
                    id,
                    name,
                    department,
                    designation,
                    date,
                    salary,
                    email,
                    phone,
                    status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Record updated successfully."
            );

            clearFields();
            loadEmployees();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update record."
            );
        }
    }

    // ================= DELETE =================

    private void deleteEmployee() {

        int row =
                tblEmployee.getSelectedRow();

        if (row == -1) {
            return;
        }

        int modelRow =
                tblEmployee.convertRowIndexToModel(row);

        int id =
                Integer.parseInt(
                        tblEmployee
                        .getModel()
                        .getValueAt(modelRow, 0)
                        .toString()
                );

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this employee?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            try {

                dao.deleteEmployee(id);

                JOptionPane.showMessageDialog(
                        this,
                        "Record deleted successfully."
                );

                clearFields();
                loadEmployees();

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete record."
                );
            }
        }
    }

    // ================= SEARCH =================

    private void searchEmployees() {

        try {

            String value =
                    txtSearch.getText().trim();

            if (value.isEmpty()) {

                loadEmployees();
                return;
            }

            String type =
                    cmbSearchType
                    .getSelectedItem()
                    .toString();

            List<Object[]> employees =
                    dao.searchEmployees(
                            type,
                            value
                    );

            DefaultTableModel model =
                    (DefaultTableModel)
                    tblEmployee.getModel();

            model.setRowCount(0);

            for (Object[] employee : employees) {
                model.addRow(employee);
            }

            lblStatus.setText(
                    employees.size() +
                    " records found"
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Search failed."
            );
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        txtId.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtSalary.setText("");
        txtEmail.setText("");
        txtPhone.setText("");

        txtId.setEditable(true);

        cmbDepartment.setSelectedIndex(0);
        cmbDesignation.setSelectedIndex(0);

        rdoActive.setSelected(true);

        tblEmployee.clearSelection();

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        lblStatus.setText("Ready");
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new EmployeeGUI()
        );
    }
}