public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String password;
    private String customerType;

    public Customer(int customerId, String name, String email,
                    String password, String customerType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.customerType = customerType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return customerId + " - " + name + " - "
                + email + " - " + customerType;
    }
}