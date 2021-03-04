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
    private TableColumn<Appointment, String> updateAppointmentLocalDateColumn;

    @FXML
    private TableColumn<Appointment, String> updateAppointmentUTCDateColumn;

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
        updateAppointmentLocalDateColumn.setText(userLanguage.getString("LocalStartTime"));
        updateAppointmentUTCDateColumn.setText(userLanguage.getString("UTCStartTime"));
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
    void saveAppointmentHandler(ActionEvent event) throws IOException {
        int appointmentId = selectedAppointment.getAppointmentId();
        int currentUserId = selectedAppointment.getUserId();
        int customerId = selectedAppointment.getCustomerId();
        int contactId = selectedAppointment.getContactId();

        // get user's text input
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String email = emailText.getText();
        String appointmentType = appointmentTypeComboBox.getPromptText();
        String contactType = contactTypeComboBox.getPromptText();
        String start = startTimeComboBox.getPromptText();
        String end = endTimeComboBox.getPromptText();
        LocalDate selectedDate = updateAppointmentDatePicker.getValue();
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
        // if all fields are filled out, update the Contact and Appointment in the database:
        try {
            // Update the Contact:
            DBQuery.makeQuery("UPDATE contacts SET Contact_Name='" + contactType + "', Email='" + email +
                    "' WHERE Contact_ID=" + contactId);

            // Update the Appointment:
            DBQuery.makeQuery("UPDATE appointments SET Title='" + title + "', Description='" + description
                    + "', Location='" + location + "', Type='" + appointmentType + "', Start='" + startTime
                    + "', End='" + endTime + "', Create_Date=NOW(), Created_By='', Last_Update=NOW(), Last_Updated_By='', Customer_ID="
                    + customerId + ", User_ID=" + currentUserId + ", Contact_ID=" + contactId + " WHERE Appointment_ID=" + appointmentId);
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
        this.appointmentTypeComboBox.promptTextProperty().setValue(selectedAppointment.getType());
        this.emailText.setText(oldContact.getEmail());
        this.contactTypeComboBox.promptTextProperty().setValue(oldContact.getContactName());
        this.updateAppointmentDatePicker.setValue(date);
        this.startTimeComboBox.promptTextProperty().setValue(start);
        this.endTimeComboBox.promptTextProperty().setValue(end);
    }

    /** Populates Appointments table with customer's appointments
     *
     * @throws SQLException
     */
    void populateAppointmentsTable(int customerId) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            appointments = Appointment.getAppointments(customerId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        updateCustomerTableView.setItems(appointments);
        updateAppointmentIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getAppointmentId())));
        updateAppointmentCustomerIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getCustomerId())));
        updateAppointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
        updateAppointmentLocalDateColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getStart()));
        updateAppointmentLocalDateColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getEnd()));
    }

}

