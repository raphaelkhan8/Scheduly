package Model;

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private int status;

    // Setters
    public void setUserId(int customerId) {
        this.customerId = customerId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setStatus(int status) {
        // 0 = inactive; 1 = active
        this.status = status;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }
    public String getUsername() {
        return customerName;
    }
    public String getAddress() {
        return address;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getPhone() {
        return phone;
    }
    public String getCountry() {
        return country;
    }
    public int getStatus() {
        return status;
    }

}
