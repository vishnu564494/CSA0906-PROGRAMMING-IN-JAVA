import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerFrame extends JFrame {

    private Customer customer;
    private Canteen canteen;
    private JTextArea menuArea;

    public CustomerFrame(Customer customer) {

        this.customer = customer;
        this.canteen = new Canteen();

        loadFood();

        setTitle("Customer Dashboard - " + customer.getName());
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + customer.getName()
                + " (" + customer.getCustomerType() + ")"
        );

        topPanel.add(welcomeLabel);

        menuArea = new JTextArea();
        menuArea.setEditable(false);
        menuArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane =
                new JScrollPane(menuArea);

        JButton menuButton =
                new JButton("View Menu");

        JButton orderButton =
                new JButton("Place Order");

        JButton billButton =
                new JButton("View Bill");

        JButton logoutButton =
                new JButton("Logout");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(menuButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(billButton);
        buttonPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        menuButton.addActionListener(e -> displayMenu());

        orderButton.addActionListener(e -> placeOrder());

        billButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Bill will be displayed after placing an order."
                )
        );

        logoutButton.addActionListener(e -> {

            dispose();
            new LoginFrame();

        });

        setVisible(true);
    }

    private void loadFood() {

        FoodDAO dao = new FoodDAO();

        List<Food> foods = dao.getAllFood();

        for (Food food : foods) {
            canteen.addFood(food);
        }
    }

    private void displayMenu() {

        menuArea.setText("");

        menuArea.append(
                "============================================\n"
        );

        menuArea.append(
                "              CANTEEN MENU\n"
        );

        menuArea.append(
                "============================================\n"
        );

        for (Food food : canteen.getFoodList()) {

            menuArea.append(
                    food.getFoodId() + " | "
                    + food.getFoodName() + " | "
                    + food.getCategory() + " | Rs."
                    + food.getPrice() + " | Stock: "
                    + food.getStock() + "\n"
            );
        }

        menuArea.append(
                "============================================\n"
        );
    }

    private void placeOrder() {

        if (canteen.getFoodList().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No food items available."
            );

            return;
        }

        String foodIdText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Food ID:"
                );

        if (foodIdText == null) {
            return;
        }

        String quantityText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Quantity:"
                );

        if (quantityText == null) {
            return;
        }

        try {

            int foodId =
                    Integer.parseInt(foodIdText);

            int quantity =
                    Integer.parseInt(quantityText);

            Food food =
                    canteen.getFood(foodId);

            if (food == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Food ID!"
                );

                return;
            }

            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than zero."
                );

                return;
            }

            if (food.getStock() < quantity) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient stock!"
                );

                return;
            }

            Order order =
                    new Order(0, customer);

            order.addItem(food, quantity);

            canteen.reduceStock(
                    foodId,
                    quantity
            );

            OrderDAO orderDAO =
                    new OrderDAO();

            int orderId =
                    orderDAO.saveOrder(order);

            if (orderId > 0) {

                Order savedOrder =
                        new Order(orderId, customer);

                savedOrder.addItem(
                        food,
                        quantity
                );

                Bill bill =
                        new Bill(0, savedOrder);

                BillDAO billDAO =
                        new BillDAO();

                billDAO.saveBill(bill);

                bill.displayBill();

                JOptionPane.showMessageDialog(
                        this,
                        "Order placed successfully!\n"
                        + "Order ID: " + orderId
                );

                displayMenu();

            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );

        } catch (InsufficientStockException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order error: "
                    + e.getMessage()
            );
        }
    }
}