package Controller;

import Database.DBQuery;
import Model.Appointment;
import Model.Customer;
import Model.SessionHandler;
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
    private ComboBox<?> startTimeComboBox;

    @FXML
    private DatePicker addAppointmentDatePicker;

    @FXML
    private TextField locationText;

    @FXML
    private ComboBox<?> endTimeComboBox;

    @FXML
    private Label contactTypeLabel;

    @FXML
    private TextField contactTypeText;

    @FXML
    private Label appointmentIdLabel;

    @FXML
    private TextField appointmentIdText;

    @FXML
    private Label customerIdLabel;

    @FXML
    private TextField customerIdText;

    @FXML
    private Label assignedContactLabel;

    @FXML
    private ComboBox<String> assignedContactComboBox;

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
    /** conatiner for customer's appointments */
    ObservableList<Appointment> allAppointments;
    /** container to hold all contact's names */
    ObservableList<String> contactsList = FXCollections.observableArrayList();
    /** var to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initilization Override: Populate contacts combo-box with contact names and
     *  change text to match user's language upon initialization
      */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateContactComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        contactTypeLabel.setText(userLanguage.getString("ContactType"));
        assignedContactLabel.setText(userLanguage.getString("AssignedContact"));
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
    void saveAppointmentHandler(ActionEvent event) {

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

    /** Populates Contact combo-box (drop-down) with contact names stored in database
     *
     * @throws SQLException
     */
    void populateContactComboBox() throws SQLException {
        ResultSet contacts = DataRetriever.getAllContacts();
        while (contacts.next()) {
            contactsList.add(contacts.getString(2));
        }
        assignedContactComboBox.setItems(contactsList);
    }

    /** Populates Appointments table with customer's appointments
     *
     * @throws SQLException
     */
    void populateAppointmentsTable(int customerId) throws SQLException {
        ResultSet appointments = DataRetriever.getCustomerAppointments(customerId);
        while (appointments.next()) {
            int appointmentId = appointments.getInt("Appointment_ID");
            String title = appointments.getString("Title");
            String description = appointments.getString("Description");
            String location = appointments.getString("Location");
            String type = appointments.getString("Type");
            String start = appointments.getString("Start");
            String end = appointments.getString("End");
            int userId = appointments.getInt("User_Id");
            int contactId = appointments.getInt("Contact_ID");
            Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            allAppointments.add(newAppointment);
        }
        addAppointmentTableView.setItems(allAppointments);
        addAppointmentIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getCustomerId())));
        addAppointmentCustomerIDColumn.setCellValueFactory(apt -> new SimpleStringProperty(Integer.toString(apt.getValue().getCustomerId())));
        addAppointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
    }

}

