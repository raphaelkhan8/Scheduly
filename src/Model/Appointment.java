package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;

public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private LocalTime time;
    private String customerName;
    private int customerId;
    private int userId;
    private int contactId;
    // container for all appointments
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public Appointment() { }

    public Appointment(int appointmentId, String title, String description, String location, String type, String start, String end, LocalTime time, String customerName, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.time = time;
        this.customerName = customerName;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
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
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
    public LocalTime getTime() {
        return time;
    }
    public String getCustomerName() {
        return customerName;
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

}
