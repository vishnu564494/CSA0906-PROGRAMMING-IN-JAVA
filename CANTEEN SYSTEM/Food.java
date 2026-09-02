public class Food {
    private int foodId;
    private String foodName;
    private String category;
    private double price;
    private int stock;

    public Food(int foodId, String foodName, String category, double price, int stock) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return foodId + " - " + foodName +
               " | " + category +
               " | Rs." + price +
               " | Stock: " + stock;
    }
}