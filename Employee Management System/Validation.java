public class Validation {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidSalary(String salary) {
        try {
            double s = Double.parseDouble(salary);
            return s >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return email.matches(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        );
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }
}