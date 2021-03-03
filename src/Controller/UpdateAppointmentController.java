package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.SessionHandler;
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
import java.sql.SQLException;
import java.time.LocalDate;
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
    private TableColumn<Appointment, Integer> updateAppointmentCustomerIDColumn;

    @FXML
    private TableColumn<Appointment, Integer> updateAppointmentIDColumn;

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
    void saveAppointmentHandler(ActionEvent event) {

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
        System.out.println(oldContact.getContactId());
        System.out.println(oldContact.getContactName());
        System.out.println(oldContact.getEmail());
        String start = startTimeWithDate.substring(startTimeWithDate.indexOf(" ") + 1);
        String end = endTimeWithDate.substring(endTimeWithDate.indexOf(" ") + 1);

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

}

