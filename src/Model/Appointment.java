package Model;

import Controller.LoginController;
import Database.DBQuery;
import Utils.DataRetriever;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private int customerId;
    private int userId;
    private int contactId;
    private String customerName;
    private String email;

    public Appointment() { }

    public Appointment(int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId, String customerName, String email) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.customerName = customerName;
        this.email = email;
    }

    // Setters
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setEmail(String email) { this.email = email; }

    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLocation() {
        return location;
    }
    public String getType() {
        return type;
    }
    public String getStart() {
        return start;
    }
    public String getEnd() {
        return end;
    }
    public int getCustomerId() {
        return customerId;
    }
    public int getUserId() {
        return userId;
    }
    public int getContactId() {
        return contactId;
    }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }

    /** Returns all of the appointments from the database
     *
     * @param customerId - int that corresponds to the customer who's appointments are to be retrieved from
     *                   the database. If customerId is negative, all the appointments will be retrieved.
     * @return - returns an ObservableList containing all of the applicable appointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointments(int customerId) throws SQLException {
        ResultSet appointments;
        String dbQuery = "";
        if (customerId < 0) {
            dbQuery = "SELECT a.*, cust.Customer_Name, c.Email FROM appointments AS a JOIN customers AS cust ON cust.Customer_ID = a.Customer_ID JOIN contacts AS c ON c.Contact_ID = a.Contact_ID";
        } else {
            dbQuery = "SELECT a.*, cust.Customer_Name, c.Email FROM appointments AS a JOIN customers AS cust ON cust.Customer_ID = a.Customer_ID JOIN contacts AS c ON c.Contact_ID = a.Contact_ID WHERE a.Customer_ID=" + customerId;
        }
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        DBQuery.makeQuery(dbQuery);
        appointments = DBQuery.getResult();
        while (appointments.next()) {
            int aptId = appointments.getInt("Appointment_ID");
            String title = appointments.getString("Title");
            String desc = appointments.getString("Description");
            String loc = appointments.getString("Location");
            String type = appointments.getString("Type");
            String start = appointments.getString("Start");
            String end = appointments.getString("End");
            int custId = appointments.getInt("Customer_ID");
            int userId = appointments.getInt("User_ID");
            int contactId = appointments.getInt("Contact_ID");
            String customerName = appointments.getString("cust.Customer_Name");
            String email = appointments.getString("c.Email");

            Appointment apt = new Appointment(aptId, title, desc, loc, type, start, end, custId, userId, contactId, customerName, email);
            allAppointments.add(apt);
        }
        return allAppointments;
    }

    /** Returns a boolean based on whether the passed-in appointment times overlap with an appointment in the database
     *
     * @param start - the start time of the Appointment (String)
     * @param end - tne end time of the Appointment (String)
     * @return - the Boolean value (true or false)
     * @throws SQLException
     */
    public static int checkForOverlappingApts(String start, String end) throws SQLException {
        int userId = LoginController.getCurrentUser();

        DBQuery.makeQuery("SELECT Appointment_ID FROM appointments WHERE User_ID = " + userId +
                " AND (Start >= '" + start + "' AND Start < '" + end +"') OR (End  > '" + start +
                "' AND End < '" + end +"')");
        ResultSet rs = DBQuery.getResult();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    /** returns the info of any upcoming appointments (within 15 minutes) when a user logs in
     *
     * @param userId - the int corresponding to the logged-in user
     * @return - the ObservableList containing the upcoming Appointment info (Strings)
     * @throws SQLException
     */
    public static ObservableList<String> getUpcomingAppointment(int userId) throws SQLException {
        ObservableList<String> upcomingAptInfo = FXCollections.observableArrayList();

        LocalDateTime loginTime = LocalDateTime.now();
        LocalDateTime loginTimePlus15 = loginTime.plus(15, ChronoUnit.MINUTES);
        int cutOffPoint = loginTime.toString().indexOf(".");
        String loginT = loginTime.toString().replace("T", " ").substring(0, cutOffPoint);
        String loginTPlus15 = loginTimePlus15.toString().replace("T", " ").substring(0, cutOffPoint);
        String formattedLoginT = DataRetriever.convertLocalTimeToUTC(loginT);
        String formattedLoginTPlus15 = DataRetriever.convertLocalTimeToUTC(loginTPlus15);

        DBQuery.makeQuery("SELECT Appointment_ID, Title, Type, Start FROM appointments WHERE User_ID = " + userId + " AND Start BETWEEN '" + formattedLoginT + "' AND '" + formattedLoginTPlus15 +"'");
        ResultSet rs = DBQuery.getResult();
        while (rs.next()) {
            upcomingAptInfo.add(rs.getString(1));
            upcomingAptInfo.add(rs.getString(2));
            upcomingAptInfo.add(rs.getString(3));
            upcomingAptInfo.add(rs.getString(4));
        }
        return upcomingAptInfo;
    }
}
