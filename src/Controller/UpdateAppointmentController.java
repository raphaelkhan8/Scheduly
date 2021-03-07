package Controller;

import Database.DBQuery;
import Model.*;
import Utils.AlertMessages;
import Utils.DataRetriever;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {

    @FXML
    private Label updateAppointmentHeader;

    @FXML
    private Label updateAppointmentHeaderText;

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
    private DatePicker updateAppointmentDatePicker;

    @FXML
    private TextField locationText;

    @FXML
    private ComboBox<String> appointmentTypeComboBox;

    @FXML
    private ComboBox<String> endTimeComboBox;

    @FXML
    private Label contactTypeLabel;

    @FXML
    private ComboBox<String> contactTypeComboBox;

    @FXML
    private Label appointmentIdLabel;

    @FXML
    private TextField appointmentIdText;

    @FXML
    private Label customerIdLabel;

    @FXML
    private TextField customerIdText;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailText;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Appointment> updateCustomerTableView;

    @FXML
    private TableColumn<Appointment, String> updateAppointmentCustomerIDColumn;

    @FXML
    private TableColumn<Appointment, String> updateAppointmentIDColumn;

    @FXML
    private TableColumn<Appointment, String> updateAppointmentLocationColumn;

    @FXML
    private TableColumn<Appointment, String> updateAppointmentStartColumn;

    @FXML
    private TableColumn<Appointment, String> updateAppointmentEndColumn;

    @FXML
    private Label updateAppointmentTableHeaderText;

    /** container to hold selected appointment */
    Appointment selectedAppointment;
    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: populate combo-boxes and change text to match user's language
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateComboBoxes();

        cancelButton.setText(userLanguage.getString("cancelButton"));
        saveAppointmentButton.setText(userLanguage.getString("saveButton"));
        updateAppointmentHeader.setText(userLanguage.getString("updateAppointmentHeader"));
        updateAppointmentHeaderText.setText(userLanguage.getString("updateAppointmentHeaderText"));
        updateAppointmentTableHeaderText.setText(userLanguage.getString("addAppointmentTableViewHeaderText"));
        appointmentIdLabel.setText(userLanguage.getString("AppointmentID"));
        customerIdLabel.setText(userLanguage.getString("CustomerID"));
        titleLabel.setText(userLanguage.getString("Title"));
        descriptionLabel.setText(userLanguage.getString("Description"));
        locationLabel.setText(userLanguage.getString("Location"));
        contactTypeLabel.setText(userLanguage.getString("ContactType"));
        emailLabel.setText(userLanguage.getString("Email"));
        dateLabel.setText(userLanguage.getString("Date"));
        startTimeLabel.setText(userLanguage.getString("StartTime"));
        endTimeLabel.setText(userLanguage.getString("EndTime"));
        updateAppointmentIDColumn.setText(userLanguage.getString("AppointmentID"));
        updateAppointmentCustomerIDColumn.setText(userLanguage.getString("CustomerID"));
        updateAppointmentLocationColumn.setText(userLanguage.getString("Location"));
        updateAppointmentStartColumn.setText(userLanguage.getString("LocalStartTime"));
        updateAppointmentEndColumn.setText(userLanguage.getString("LocalEndTime"));
    }

    /** changes view back to Appointment Manager
     *
     * @param event - the Event that triggers this function call (click Cancel button)
     * @throws IOException
     */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void saveAppointmentHandler(ActionEvent event) throws IOException, SQLException {
        int appointmentId = selectedAppointment.getAppointmentId();
        int currentUserId = selectedAppointment.getUserId();
        int customerId = selectedAppointment.getCustomerId();
        int contactId = selectedAppointment.getContactId();

        // get user's text input
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String email = emailText.getText();
        String appointmentType = appointmentTypeComboBox.getSelectionModel().getSelectedItem();
        String contactType = contactTypeComboBox.getSelectionModel().getSelectedItem();
        String start = startTimeComboBox.getSelectionModel().getSelectedItem();
        String end = endTimeComboBox.getSelectionModel().getSelectedItem();
        LocalDate selectedDate = updateAppointmentDatePicker.getValue();
        String startTime = selectedDate + " " + start;
        String endTime = selectedDate + " " + end;
        // verify that all text fields were filled out
        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || email.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingFieldMessage"));
            return;
        }
        // verify that a date was picked
        if (selectedDate == null) {
            AlertMessages.errorMessage(userLanguage.getString("selectDateMsg"));
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
        // verify that appointment times do not overlap with another
        Boolean duplicateAptTimes = Appointment.checkForOverlappingApts(startTime, endTime);
        if (duplicateAptTimes == true) {
            AlertMessages.errorMessage(userLanguage.getString("overlapAptMsg"));
            return;
        }
        // if all fields are filled out, update the Appointment and its associated Contact
        try {
            DBQuery.makeQuery("UPDATE contacts c, appointments a SET c.Contact_Name='" + contactType + "', c.Email='" + email
                    + "', a.Title='" + title + "', a.Description='" + description + "', a.Location='" + location
                    + "', a.Type='" + appointmentType + "', a.Start='" + startTime + "', a.End='" + endTime
                    + "', a.Create_Date=NOW(), a.Created_By='', a.Last_Update=NOW(), a.Last_Updated_By='', a.Customer_ID="
                    + customerId + ", a.User_ID=" + currentUserId + ", a.Contact_ID=" + contactId + " WHERE a.Contact_ID=c.Contact_ID AND a.Appointment_ID=" + appointmentId);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        AlertMessages.alertMessage(userLanguage.getString("Appointment") + " #" + appointmentId + " (" + appointmentType + ") " + userLanguage.getString("hasBeenUpdated"));
        // Afterwards, go back to Customer Table view
        cancelView(event);
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

    /** Gets selected appointment from AppointmentManager view to populate UpdateAppointment view fields
     *
     * @param appointment - the Appointment object selected to be updated
     * @throws SQLException
     */
    public void getSelectedAppointment(Appointment appointment) throws SQLException {
        selectedAppointment = appointment;
        Contact oldContact = Contact.getEmail(selectedAppointment.getContactId());
        String startTimeWithDate = selectedAppointment.getStart();
        String endTimeWithDate = selectedAppointment.getEnd();
        LocalDate date = LocalDate.parse(startTimeWithDate.substring(0, startTimeWithDate.indexOf(" ")));
        String start = startTimeWithDate.substring(startTimeWithDate.indexOf(" ") + 1);
        String end = endTimeWithDate.substring(endTimeWithDate.indexOf(" ") + 1);

        populateAppointmentsTable(selectedAppointment.getCustomerId());

        this.appointmentIdText.setText(Integer.toString(selectedAppointment.getAppointmentId()));
        this.customerIdText.setText(Integer.toString(selectedAppointment.getCustomerId()));
        this.titleText.setText(selectedAppointment.getTitle());
        this.descriptionText.setText((selectedAppointment.getDescription()));
        this.locationText.setText(selectedAppointment.getLocation());
        this.appointmentTypeComboBox.setValue(selectedAppointment.getType());
        this.emailText.setText(selectedAppointment.getEmail());
        this.contactTypeComboBox.setValue(oldContact.getContactName());
        this.updateAppointmentDatePicker.setValue(date);
        this.startTimeComboBox.setValue(start);
        this.endTimeComboBox.setValue(end);
    }

    /** Populates Appointments table with customer's appointments
     *
     * @throws SQLException
     */
    void populateAppointmentsTable(int customerId) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            appointments = Appointment.getAppointments(-1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        updateCustomerTableView.setItems(appointments);
        updateAppointmentIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getAppointmentId())));
        updateAppointmentCustomerIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getCustomerId())));
        updateAppointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
        updateAppointmentStartColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getStart()));
        updateAppointmentEndColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getEnd()));
    }

}

