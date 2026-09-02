import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {

    private FoodDAO foodDAO;
    private OrderDAO orderDAO;
    private BillDAO billDAO;
    private JTextArea outputArea;

    public AdminFrame() {

        foodDAO = new FoodDAO();
        orderDAO = new OrderDAO();
        billDAO = new BillDAO();

        setTitle("Canteen Admin Dashboard");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel title =
                new JLabel(
                        "CANTEEN ADMIN DASHBOARD",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(
                new Font("Monospaced", Font.PLAIN, 13)
        );

        JScrollPane scrollPane =
                new JScrollPane(outputArea);

        JButton viewFoodButton =
                new JButton("View Food");

        JButton addFoodButton =
                new JButton("Add Food");

        JButton updateFoodButton =
                new JButton("Update Food");

        JButton deleteFoodButton =
                new JButton("Delete Food");

        JButton viewOrdersButton =
                new JButton("View Orders");

        JButton updateOrderButton =
                new JButton("Update Order");

        JButton viewBillsButton =
                new JButton("View Bills");

        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 4, 5, 5)
        );

        buttonPanel.add(viewFoodButton);
        buttonPanel.add(addFoodButton);
        buttonPanel.add(updateFoodButton);
        buttonPanel.add(deleteFoodButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(updateOrderButton);
        buttonPanel.add(viewBillsButton);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        viewFoodButton.addActionListener(
                e -> viewFood()
        );

        addFoodButton.addActionListener(
                e -> addFood()
        );

        updateFoodButton.addActionListener(
                e -> updateFood()
        );

        deleteFoodButton.addActionListener(
                e -> deleteFood()
        );

        viewOrdersButton.addActionListener(
                e -> viewOrders()
        );

        updateOrderButton.addActionListener(
                e -> updateOrder()
        );

        viewBillsButton.addActionListener(
                e -> viewBills()
        );

        setVisible(true);
    }

    private void viewFood() {

        List<Food> foods =
                foodDAO.getAllFood();

        outputArea.setText(
                "============== FOOD MENU ==============\n"
        );

        for (Food food : foods) {
            outputArea.append(
                    food + "\n"
            );
        }
    }

    private void addFood() {

        try {

            String name =
                    JOptionPane.showInputDialog(
                            this,
                            "Food Name:"
                    );

            String category =
                    JOptionPane.showInputDialog(
                            this,
                            "Category:"
                    );

            double price =
                    Double.parseDouble(
                            JOptionPane.showInputDialog(
                                    this,
                                    "Price:"
                            )
                    );

            int stock =
                    Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    this,
                                    "Stock:"
                            )
                    );

            Food food =
                    new Food(
                            0,
                            name,
                            category,
                            price,
                            stock
                    );

            foodDAO.addFood(food);

            JOptionPane.showMessageDialog(
                    this,
                    "Food added successfully."
            );

            viewFood();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter valid price and stock."
            );
        }
    }

    private void updateFood() {

        try {

            int id =
                    Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    this,
                                    "Food ID:"
                            )
                    );

            String name =
                    JOptionPane.showInputDialog(
                            this,
                            "New Food Name:"
                    );

            String category =
                    JOptionPane.showInputDialog(
                            this,
                            "New Category:"
                    );

            double price =
                    Double.parseDouble(
                            JOptionPane.showInputDialog(
                                    this,
                                    "New Price:"
                            )
                    );

            int stock =
                    Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    this,
                                    "New Stock:"
                            )
                    );

            Food food =
                    new Food(
                            id,
                            name,
                            category,
                            price,
                            stock
                    );

            foodDAO.updateFood(food);

            JOptionPane.showMessageDialog(
                    this,
                    "Food updated successfully."
            );

            viewFood();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter valid values."
            );
        }
    }

    private void deleteFood() {

        try {

            int id =
                    Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    this,
                                    "Enter Food ID:"
                            )
                    );

            foodDAO.deleteFood(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Food deleted successfully."
            );

            viewFood();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid Food ID."
            );
        }
    }

    private void viewOrders() {

        orderDAO.displayAllOrders();

        outputArea.setText(
                "Orders are displayed in the Command Prompt.\n"
        );
    }

    private void updateOrder() {

        try {

            int orderId =
                    Integer.parseInt(
                            JOptionPane.showInputDialog(
                                    this,
                                    "Order ID:"
                            )
                    );

            String status =
                    JOptionPane.showInputDialog(
                            this,
                            "New Status:\n"
                            + "Pending / Preparing / Ready / Completed"
                    );

            orderDAO.updateOrderStatus(
                    orderId,
                    status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Order status updated."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid Order ID."
            );
        }
    }

    private void viewBills() {

        billDAO.displayAllBills();

        outputArea.setText(
                "Bills are displayed in the Command Prompt.\n"
        );
    }
}