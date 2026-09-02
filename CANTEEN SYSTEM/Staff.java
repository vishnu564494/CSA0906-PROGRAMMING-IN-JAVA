public class Staff extends Customer {

    public Staff(int customerId, String name, String email,
                 String password) {
        super(customerId, name, email, password, "Staff");
    }

    @Override
    public String getCustomerType() {
        return "Staff";
    }
}