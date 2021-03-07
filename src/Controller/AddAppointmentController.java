package Controller;

import Database.DBQuery;
import Model.Appointment;
import Model.Customer;
import Model.SessionHandler;
import Utils.AlertMessages;
import Utils.DataRetriever;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Appointment, String> addAppointmentStartColumn;

    @FXML
    private TableColumn<Appointment, String> addAppointmentEndColumn;

    @FXML
    private Label addAppointmentTableHeaderText;

    /** var to hold current user's Id */
    int currentUserId;
    /** container to hold selected customer */
    Customer selectedCustomer;
    /** container to hold user's language */
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
        addAppointmentStartColumn.setText(userLanguage.getString("LocalStartTime"));
        addAppointmentEndColumn.setText(userLanguage.getString("LocalEndTime"));
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

    /** Performs input validation prior to saving new appointment
     *
     * @param event - the Event that triggers this function call (click Save button)
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void saveAppointmentHandler(ActionEvent event) throws IOException, SQLException {
        int appointmentId = 1;
        int contactId = 1;
        // get user's text input
        String customerId = customerIdText.getText();
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

        System.out.println(startTime);
        System.out.println(endTime);

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
        // verify that start and end times were selected
        if (start == null || end == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectTimeMsg"));
            return;
        }
        // verify that end time is after start time
        int startHour = Integer.parseInt(start.substring(0, start.indexOf(":")));
        int endHour = Integer.parseInt(end.substring(0, end.indexOf(":")));
        if (endHour <= startHour) {
            AlertMessages.errorMessage(userLanguage.getString("timeErrorMsg"));
            return;
        }
        // verify that the date is in the future
        if (selectedDate.compareTo(LocalDate.now()) < 0 || (selectedDate.compareTo(LocalDate.now()) == 0 & startHour <= LocalDateTime.now().getHour())) {
            AlertMessages.errorMessage(userLanguage.getString("invalidDateMsg"));
            return;
        }
        // verify that appointment times do not overlap with another
        String formattedStartTime = DataRetriever.convertLocalTimeToUTC(startTime);
        String formattedEndTime = DataRetriever.convertLocalTimeToUTC(endTime);
        System.out.println(formattedStartTime);
        System.out.println(formattedEndTime);
        int duplicateAptTimes = Appointment.checkForOverlappingApts(formattedStartTime, formattedEndTime);
        if (duplicateAptTimes > -1) {
            AlertMessages.errorMessage(userLanguage.getString("overlapAptMsg"));
            return;
        }
        // if all fields are filled out, add the Contact and Appointment to the database:
        try {
            // get the last contactId and AppointmentId in db and increment by one to get the new primary keys
            DBQuery.makeQuery("SELECT MAX(c.Contact_ID), MAX(a.Appointment_ID) FROM contacts AS c CROSS JOIN appointments AS a");
            ResultSet lastIds = DBQuery.getResult();
            if (lastIds.next()) {
                contactId = lastIds.getInt(1);
                appointmentId = lastIds.getInt(2);
                contactId++;
                appointmentId++;

            }
            // add the contact to the database:
            DBQuery.makeQuery("INSERT INTO contacts SET Contact_ID=" + contactId + ", Contact_Name='" +
                    contactType + "', Email='" + email + "'");
            // add the appointment to the database:
            DBQuery.makeQuery("INSERT INTO appointments SET Appointment_ID=" + appointmentId + ", Title='" +
                    title + "', Description='" + description + "', Location='" + location + "', Type='" + appointmentType +
                    "', Start='" + formattedStartTime + "', End='" + formattedEndTime + "', Create_Date=NOW(), Created_By='', Last_Update=NOW(), Last_Updated_By='', Customer_ID="
                    + Integer.parseInt(customerId) + ", User_ID=" + currentUserId + ", Contact_ID=" + contactId);
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
        populateAppointmentsTable();
    }

    /** Populates all four combo boxes with possible user choices
     *
     */
    void populateComboBoxes() {
        contactTypeComboBox.setItems(DataRetriever.getContactTypes());
        appointmentTypeComboBox.setItems(DataRetriever.getAppointmentTypes());
        startTimeComboBox.setItems(DataRetriever.getTimes());
        endTimeComboBox.setItems(DataRetriever.getTimes());
    }

    /** Populates Appointments table with customer's appointments
     * NOTE: Lamda expression used: Lamdas were used to extract properties out of an array of Appointment objects.
     *       They were beneficial here as new Appointment objects did not have to be created, thus reducing the
     *       lines of code and lowering the time and space complexity.
     * @throws SQLException
     */
    void populateAppointmentsTable() throws SQLException {
        ObservableList<Appointment> appointments = Appointment.getAppointments(-1);
        addAppointmentTableView.setItems(appointments);
        addAppointmentIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getAppointmentId())));
        addAppointmentCustomerIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getCustomerId())));
        addAppointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
        addAppointmentStartColumn.setCellValueFactory(apt -> new SimpleStringProperty(DataRetriever.convertUTCTimeToLocal(apt.getValue().getStart())));
        addAppointmentEndColumn.setCellValueFactory(apt -> new SimpleStringProperty(DataRetriever.convertUTCTimeToLocal(apt.getValue().getEnd())));
    }

}

