package Controller;

import Database.DBQuery;
import Model.Appointment;
import Model.Customer;
import Model.SessionHandler;
import Utils.AlertMessages;
import Utils.DataRetriever;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private Label addAppointmentHeader;

    @FXML
    private Label addAppointmentHeaderText;

    @FXML
    private Label locationLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private ComboBox<String> startTimeComboBox;

    @FXML
    private DatePicker addAppointmentDatePicker;

    @FXML
    private TextField locationText;

    @FXML
    private ComboBox<String> endTimeComboBox;

    @FXML
    private Label appointmentTypeLabel;

    @FXML
    private ComboBox<String> appointmentTypeComboBox;

    @FXML
    private Label appointmentIdLabel;

    @FXML
    private TextField appointmentIdText;

    @FXML
    private Label customerIdLabel;

    @FXML
    private TextField customerIdText;

    @FXML
    private Label contactTypeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailText;

    @FXML
    private ComboBox<String> contactTypeComboBox;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Appointment> addAppointmentTableView;

    @FXML
    private TableColumn<Appointment, String> addAppointmentCustomerIDColumn;

    @FXML
    private TableColumn<Appointment, String> addAppointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> addAppointmentLocationColumn;

    @FXML
    private TableColumn<Appointment, String> addAppointmentLocalDateColumn;

    @FXML
    private TableColumn<Appointment, String> addAppointmentUTCDateColumn;

    @FXML
    private Label addAppointmentTableHeaderText;

    /** var to hold current user's Id */
    int currentUserId;
    /** container to hold selected customer */
    Customer selectedCustomer;
//    /** container for customer's appointments */
//    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
//    /** container to hold all contact's names */
//    ObservableList<String> contactsList = FXCollections.observableArrayList();
    /** var to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: Populate combo-boxes and change text to match user's language
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateComboBoxes();

        appointmentIdText.setTooltip(new Tooltip(userLanguage.getString("appointmentIDTooltip")));
        cancelButton.setText(userLanguage.getString("cancelButton"));
        saveAppointmentButton.setText(userLanguage.getString("saveButton"));
        addAppointmentHeader.setText(userLanguage.getString("addAppointmentHeader"));
        addAppointmentHeaderText.setText(userLanguage.getString("addAppointmentHeaderText"));
        addAppointmentTableHeaderText.setText(userLanguage.getString("addAppointmentTableViewHeaderText"));
        appointmentIdLabel.setText(userLanguage.getString("AppointmentID"));
        customerIdLabel.setText(userLanguage.getString("CustomerID"));
        titleLabel.setText(userLanguage.getString("Title"));
        descriptionLabel.setText(userLanguage.getString("Description"));
        locationLabel.setText(userLanguage.getString("Location"));
        appointmentTypeLabel.setText(userLanguage.getString("AppointmentType"));
        emailLabel.setText(userLanguage.getString("Email"));
        contactTypeLabel.setText(userLanguage.getString("ContactType"));
        dateLabel.setText(userLanguage.getString("Date"));
        startTimeLabel.setText(userLanguage.getString("StartTime"));
        endTimeLabel.setText(userLanguage.getString("EndTime"));
        addAppointmentIDColumn.setText(userLanguage.getString("AppointmentID"));
        addAppointmentCustomerIDColumn.setText(userLanguage.getString("CustomerID"));
        addAppointmentLocationColumn.setText(userLanguage.getString("Location"));
        addAppointmentLocalDateColumn.setText(userLanguage.getString("LocalStartTime"));
        addAppointmentUTCDateColumn.setText(userLanguage.getString("UTCStartTime"));
    }

    /** Changes view to Customers Table page
     *
     * @param event - the event that triggers this function call (click Cancel button)
     * @throws IOException
     */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void saveAppointmentHandler(ActionEvent event) throws IOException {
        int appointmentId = 1;
        int contactId = 1;
        // get user's text input
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String email = emailText.getText();
        String appointmentType = appointmentTypeComboBox.getSelectionModel().getSelectedItem();
        String contactType = contactTypeComboBox.getSelectionModel().getSelectedItem();
        String start = startTimeComboBox.getSelectionModel().getSelectedItem();
        String end = endTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = addAppointmentDatePicker.getValue();
        String startTime = selectedDate + " " + start;
        String endTime = selectedDate + " " + end;
        // verify that all text fields were filled out
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || email.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingFieldMessage"));
            return;
        }
        // verify that an appointment type was selected
        if (appointmentType == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectAppointmentTypeMsg"));
            return;
        }
        // verify that an assigned contact was selected
        if (contactType == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectContactTypeMsg"));
            return;
        }
        // verify that a date was picked
        if (selectedDate == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectDateMsg"));
            return;
        }
        // verify that a date was picked
        if (selectedDate == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectDateMsg"));
            return;
        }
        // verify that start and end times were selected
        if (start == null || end == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectTimeMsg"));
            return;
        }
        // verify that end time is after start time
        int startHour = Integer.parseInt(start.substring(0, start.indexOf(":")));
        int endHour = Integer.parseInt(end.substring(0, end.indexOf(":")));
        if (endHour < startHour) {
            AlertMessages.errorMessage(userLanguage.getString("timeErrorMsg"));
            return;
        }
        // verify that the date is in the future
        if (selectedDate.compareTo(LocalDate.now()) < 0 || (selectedDate.compareTo(LocalDate.now()) == 0 & startHour <= LocalDateTime.now().getHour() + 1)) {
            AlertMessages.errorMessage(userLanguage.getString("invalidDateMsg"));
            return;
        }
        // if all fields are filled out, add the Contact and Appointment to the database:
        try {
            /// Add the Contact:
            // get the last contactId in db and increment by one to get the new contactId
            DBQuery.makeQuery("SELECT MAX(Contact_ID) FROM contacts");
            ResultSet lastContactId = DBQuery.getResult();
            if (lastContactId.next()) {
                contactId = lastContactId.getInt(1);
                contactId++;
            }
            // then add the contact to the database:
            DBQuery.makeQuery("INSERT INTO contacts SET Contact_ID=" + contactId + ", Contact_Name='" +
                    contactType + "', Email='" + email + "'");

            /// Add the Appointment:
            // get the last appointmentId in db and increment by one to get the new appointmentId
            DBQuery.makeQuery("SELECT MAX(Appointment_ID) FROM appointments");
            ResultSet lastAppointId = DBQuery.getResult();
            if (lastAppointId.next()) {
                appointmentId = lastAppointId.getInt(1);
                appointmentId++;
            }
            // then add the appointment to the database:
            DBQuery.makeQuery("INSERT INTO appointments SET Appointment_ID=" + appointmentId + ", Title='" +
                    title + "', Description='" + description + "', Location='" + location + "', Type='" + appointmentType +
                    "', Start='" + startTime + "', End='" + endTime + "', Create_Date=NOW(), Created_By='', Last_Update=NOW(), Last_Updated_By='', Customer_ID="
                    + selectedCustomer.getCustomerId() + ", User_ID=" + currentUserId + ", Contact_ID=" + contactId);
            AlertMessages.alertMessage(userLanguage.getString("addAppointmentSuccessMsg"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            AlertMessages.errorMessage(userLanguage.getString("addAppointmentErrorMsg"));
        }
        // Afterwards, go back to Customer Table view
        cancelView(event);
    }

    /** Retrieves selected customers info from previous view and populates Appointment table
     *
     * @param customer - the Customer object to add an Appointment to
     * @throws SQLException
     */
    public void getSelectedCustomer(Customer customer) throws SQLException {

        selectedCustomer = customer;
        Customer selectedCustomer = (Customer) customer;

        // get the logged-in user's userId
        currentUserId = LoginController.getCurrentUser();

        this.customerIdText.setText(Integer.toString(selectedCustomer.getCustomerId()));
        populateAppointmentsTable(selectedCustomer.getCustomerId());
    }

    /** Populates all four combo boxes with possible user choices
     *
     */
    void populateComboBoxes() {
        ObservableList<String> contactTypes = FXCollections.observableArrayList("Primary", "Work", "School");
        ObservableList<String> meetingTypes = FXCollections.observableArrayList("Virtual", "In-Person", "Telephone", "Whatever works");
        ObservableList<String> times = FXCollections.observableArrayList("06:00:00","07:00:00","08:00:00","09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00");
        contactTypeComboBox.setItems(contactTypes);
        appointmentTypeComboBox.setItems(meetingTypes);
        startTimeComboBox.setItems(times);
        endTimeComboBox.setItems(times);
    }

    /** Populates Appointments table with customer's appointments
     *
     * @throws SQLException
     */
    void populateAppointmentsTable(int customerId) throws SQLException {
        ObservableList<Appointment> appointments = Appointment.getAppointments(customerId);
        addAppointmentTableView.setItems(appointments);
        addAppointmentIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getAppointmentId())));
        addAppointmentCustomerIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getCustomerId())));
        addAppointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
        addAppointmentLocalDateColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getStart()));
        addAppointmentLocalDateColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getEnd()));
    }

}

