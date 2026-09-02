public class Faculty extends Customer {

    public Faculty(int customerId, String name, String email,
                   String password) {
        super(customerId, name, email, password, "Faculty");
    }

    @Override
    public String getCustomerType() {
        return "Faculty";
    }
}