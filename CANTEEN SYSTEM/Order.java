import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<Food> foodItems;
    private List<Integer> quantities;
    private double totalAmount;
    private String status;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.foodItems = new ArrayList<>();
        this.quantities = new ArrayList<>();
        this.totalAmount = 0.0;
        this.status = "Pending";
    }

    public void addItem(Food food, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be greater than zero."
            );
        }

        foodItems.add(food);
        quantities.add(quantity);

        totalAmount += food.getPrice() * quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Food> getFoodItems() {
        return foodItems;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayOrder() {
        System.out.println("\n========== ORDER ==========");
        System.out.println("Order ID : " + orderId);
        System.out.println("Customer : " + customer.getName());
        System.out.println("Type     : " + customer.getCustomerType());

        System.out.println("\nItems:");

        for (int i = 0; i < foodItems.size(); i++) {
            Food food = foodItems.get(i);
            int quantity = quantities.get(i);

            System.out.println(
                food.getFoodName() + " x " +
                quantity + " = Rs." +
                (food.getPrice() * quantity)
            );
        }

        System.out.println("----------------------------");
        System.out.println("Total    : Rs." + totalAmount);
        System.out.println("Status   : " + status);
        System.out.println("============================");
    }
}