public class Student extends Customer {

    public Student(int customerId, String name, String email,
                   String password) {
        super(customerId, name, email, password, "Student");
    }

    @Override
    public String getCustomerType() {
        return "Student";
    }
}