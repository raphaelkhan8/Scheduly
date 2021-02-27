package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;
    // container for customer's appointments
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Customer() { }

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String country, String division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
    }

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
    public void setDivision(String status) { this.division = division; }
    public void addAppointment(Appointment apt) { allAppointments.add(apt); }

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
    public String getDivision() {
        return division;
    }
    public ObservableList<Appointment> getAllAppointments() { return allAppointments; }

}
