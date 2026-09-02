public class OrderThread extends Thread {

    private Canteen canteen;
    private int foodId;
    private int quantity;
    private String customerName;

    public OrderThread(Canteen canteen, int foodId,
                       int quantity, String customerName) {

        this.canteen = canteen;
        this.foodId = foodId;
        this.quantity = quantity;
        this.customerName = customerName;
    }

    @Override
    public void run() {

        try {

            System.out.println(
                    "\n" + customerName +
                    " is placing an order..."
            );

            Food food = canteen.getFood(foodId);

            if (food == null) {
                throw new InvalidOrderException(
                        "Food item not found."
                );
            }

            canteen.reduceStock(foodId, quantity);

            System.out.println(
                    "Order completed for " +
                    customerName
            );

        } catch (InsufficientStockException e) {

            System.out.println(
                    "Stock Error for " +
                    customerName + ": " +
                    e.getMessage()
            );

        } catch (InvalidOrderException e) {

            System.out.println(
                    "Order Error for " +
                    customerName + ": " +
                    e.getMessage()
            );
        }
    }
}