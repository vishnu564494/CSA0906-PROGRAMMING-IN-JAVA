import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("   CANTEEN FOOD ORDERING AND BILLING");
        System.out.println("==========================================");
        System.out.println("Starting application...");

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}