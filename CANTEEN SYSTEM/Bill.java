public class Bill {
    private int billId;
    private Order order;
    private double subtotal;
    private double tax;
    private double totalAmount;
    private String paymentStatus;

    public Bill(int billId, Order order) {
        this.billId = billId;
        this.order = order;
        this.subtotal = order.getTotalAmount();
        this.tax = subtotal * 0.05;
        this.totalAmount = subtotal + tax;
        this.paymentStatus = "Pending";
    }

    public int getBillId() {
        return billId;
    }

    public Order getOrder() {
        return order;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void displayBill() {
        System.out.println("\n================================");
        System.out.println("          CANTEEN BILL");
        System.out.println("================================");

        System.out.println("Bill ID   : " + billId);
        System.out.println("Order ID  : " + order.getOrderId());
        System.out.println("Customer  : " + order.getCustomer().getName());

        System.out.println("--------------------------------");
        System.out.printf("Subtotal  : Rs. %.2f%n", subtotal);
        System.out.printf("Tax (5%%)  : Rs. %.2f%n", tax);
        System.out.println("--------------------------------");
        System.out.printf("TOTAL     : Rs. %.2f%n", totalAmount);
        System.out.println("Payment   : " + paymentStatus);
        System.out.println("================================");
    }
}