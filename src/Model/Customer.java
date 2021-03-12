package Model;

import Database.DBQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {

    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;
    private int divisionId;

    public Customer() { }

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String country, String division, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
        this.divisionId = divisionId;
    }

    /** Setter
     *
     * @param customerId - the int value which the Customer's Id will be set to
     */
    public void setUserId(int customerId) {
        this.customerId = customerId;
    }
    /** Setter
     *
     * @param customerName - the String value which the Customer's name will be set to
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    /** Setter
     *
     * @param address - the String value which the Customer's address will be set to
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /** Setter
     *
     * @param postalCode - the String value which the Customer's postal code will be set to
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** Setter
     *
     * @param phone - the String value which the Customer's phone number will be set to
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /** Setter
     *
     * @param country - the String value which the Customer's country will be set to
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /** Setter
     *
     * @param division - the String value which the Customer's associated division will be set to
     */
    public void setDivision(String division) { this.division = division; }
    /** Setter
     *
     * @param divisionId - the int value which the Customer's associated divisionId will be set to
     */
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }

    /** Getter
     *
     * @return - the int value corresponding to the Customer's Id
     */
    public int getCustomerId() {
        return customerId;
    }
    /** Getter
     *
     * @return - the String value corresponding to the Customer's username
     */
    public String getUsername() {
        return customerName;
    }
    /** Getter
     *
     * @return - the String value corresponding to the Customer's address
     */
    public String getAddress() {
        return address;
    }
    /** Getter
     *
     * @return - the String value corresponding to the Customer's postal code
     */
    public String getPostalCode() {
        return postalCode;
    }
    /** Getter
     *
     * @return - the String value corresponding to the Customer's postal code
     */
    public String getPhone() {
        return phone;
    }
    public String getCountry() {
        return country;
    }
    public String getDivision() {
        return division;
    }
    /** Getter
     *
     * @return - the int value corresponding to the Customer's associated Division Id
     */
    public int getDivisionId() { return divisionId; }

    /** Gets the customer name corresponding to the passed-in customerId
     *
     * @param customerId - the primary key of the customer
     * @return - the String corresponding to the passed-in customerId
     */
    public static String getCustomerName(int customerId) {
        String name = "";
        try {
            DBQuery.makeQuery("SELECT Customer_Name from customers WHERE Customer_ID=" + customerId);
            ResultSet rs = DBQuery.getResult();
            while (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return name;
    }

}
