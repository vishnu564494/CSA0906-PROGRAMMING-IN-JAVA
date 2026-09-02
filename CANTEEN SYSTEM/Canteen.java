import java.util.*;

public class Canteen {

    private List<Food> foodList;
    private Map<Integer, Food> foodMap;
    private Set<String> categories;

    public Canteen() {
        foodList = new ArrayList<>();
        foodMap = new HashMap<>();
        categories = new HashSet<>();
    }

    public void addFood(Food food) {
        foodList.add(food);
        foodMap.put(food.getFoodId(), food);
        categories.add(food.getCategory());
    }

    public void displayMenu() {

        System.out.println("\n==============================================");
        System.out.println("              CANTEEN FOOD MENU");
        System.out.println("==============================================");

        for (Food food : foodList) {
            System.out.println(food);
        }

        System.out.println("==============================================");
    }

    public Food getFood(int foodId) {

        if (foodMap.containsKey(foodId)) {
            return foodMap.get(foodId);
        }

        return null;
    }

    public synchronized void reduceStock(int foodId, int quantity)
            throws InsufficientStockException {

        Food food = getFood(foodId);

        if (food == null) {
            throw new InsufficientStockException(
                    "Food item not found."
            );
        }

        if (quantity <= 0) {
            throw new InsufficientStockException(
                    "Quantity must be greater than zero."
            );
        }

        if (food.getStock() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for " + food.getFoodName()
            );
        }

        food.setStock(food.getStock() - quantity);

        System.out.println(
                "Stock updated: " +
                food.getFoodName() +
                " | Remaining: " +
                food.getStock()
        );
    }

    public void displayCategories() {

        System.out.println("\nFood Categories:");

        Iterator<String> iterator = categories.iterator();

        while (iterator.hasNext()) {
            System.out.println("- " + iterator.next());
        }
    }

    public List<Food> getFoodList() {
        return foodList;
    }
}