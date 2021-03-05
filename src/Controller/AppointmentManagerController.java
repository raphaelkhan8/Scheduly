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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppointmentManagerController implements Initializable {

    @FXML
    private Label appointmentManagerLabel;

    @FXML
    private RadioButton viewByMonthRadioButton;

    @FXML
    private ToggleGroup appointmentRadioButtons;

    @FXML
    private RadioButton viewByWeekRadioButton;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> customerNameColumn;

    @FXML
    private TableColumn<Appointment, String> customerContactColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentStartColumn;

    @FXML
    private TableColumn<Appointment, String> appointmentEndColumn;

    @FXML
    private Button cancelButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Label viewByLabel;

    @FXML
    private ComboBox<String> viewByComboBox;

    @FXML
    private Button searchTableSorterButton;

    /** container to hold selected appointment */
    private Appointment selectedAppointment;
    /** container to hold all appointments for Calendar view */
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: Populate table with all appointments and change text to match user's language
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            appointments = Appointment.getAppointments(-1);
            populateAppointmentsTable(appointments);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        searchTableSorterButton.setText(userLanguage.getString("searchButton"));
        cancelButton.setText(userLanguage.getString("cancelButton"));
        updateAppointmentButton.setText(userLanguage.getString("updateButton"));
        deleteAppointmentButton.setText(userLanguage.getString("deleteButton"));
        appointmentManagerLabel.setText(userLanguage.getString("appointmentManagerLabel"));
        viewByWeekRadioButton.setText(userLanguage.getString("viewByWeekRadioButton"));
        viewByMonthRadioButton.setText(userLanguage.getString("viewByMonthRadioButton"));
        viewByLabel.setText(userLanguage.getString("viewBy"));
        viewByComboBox.promptTextProperty().setValue(userLanguage.getString("viewByComboBox"));
        customerNameColumn.setText(userLanguage.getString("CustomerName"));
        customerContactColumn.setText(userLanguage.getString("Email"));
        appointmentTitleColumn.setText(userLanguage.getString("Title"));
        appointmentTypeColumn.setText(userLanguage.getString("Type"));
        appointmentLocationColumn.setText(userLanguage.getString("Location"));
        appointmentDescriptionColumn.setText(userLanguage.getString("Description"));
        appointmentStartColumn.setText(userLanguage.getString("StartTime"));
        appointmentEndColumn.setText(userLanguage.getString("EndTime"));
    }

    /** Change view to Home Page
     *
     * @param event - the Event that triggers this function call (click Cancel button)
     * @throws IOException
     */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** deletes the selected Appointment
     *
     * @param event - the Event that triggers this function call (click Delete button)
     */
    @FXML
    void deleteAppointmentHandler(ActionEvent event) {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            AlertMessages.errorMessage(userLanguage.getString("deleteAppointmentNoAppointmentMessage"));
            return;
        }

        AtomicBoolean proceed = AlertMessages.confirmMessage(userLanguage.getString("appointmentDeleteConfirmMessage"));
        if (proceed.get() == true) {

            int numberOfAppointments = appointmentTableView.getItems().size();
            int selectedId = selectedAppointment.getAppointmentId();
            String appointmentType = selectedAppointment.getType();

            if (numberOfAppointments == 1) {
                appointmentTableView.getItems().remove(0);
            } else {
                ObservableList<Appointment> allAppointments, singleApt;
                allAppointments = appointmentTableView.getItems();
                singleApt = appointmentTableView.getSelectionModel().getSelectedItems();
                singleApt.forEach(allAppointments::remove);
                selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
            }

            try {
                DBQuery.makeQuery("DELETE FROM appointments WHERE Appointment_ID =" + selectedId);
                AlertMessages.alertMessage(userLanguage.getString("Appointment") + " #" + selectedId + " (" + appointmentType + ") " + userLanguage.getString("hasBeenCancelled"));
            }
            catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /** updates the selected Appointment
     *
     * @param event - the Event that triggers this function call (click Delete button)
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void updateAppointmentHandler(ActionEvent event) throws IOException, SQLException {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            AlertMessages.errorMessage(userLanguage.getString("updateAppointmentNoAppointmentMessage"));
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
        loader.load();
        UpdateAppointmentController controller = loader.getController();

        controller.getSelectedAppointment(selectedAppointment);

        Stage stage = (Stage) updateAppointmentButton.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void searchTableSorterHandler(ActionEvent event) {

    }

    /** filters the AppointmentTable to only show appointments in the current month
     *
     * @param event - the Event that triggers this function call (click View By Month radio button)
     */
    @FXML
    void viewByMonthHandler(ActionEvent event) {
        String currentMonth = LocalDate.now().getMonth().toString();
        appointments = DataRetriever.getAppointmentsByDuration(1, currentMonth);
        populateAppointmentsTable(appointments);
        viewByComboBox.setItems(DataRetriever.getMonthOptions());
    }

    /** filters the AppointmentTable to only show appointments in the current week
     *
     * @param event - the Event that triggers this function call (click View By Week radio button)
     */
    @FXML
    void viewByWeekHandler(ActionEvent event) {
        appointments = DataRetriever.getAppointmentsByDuration(0, "0");
        populateAppointmentsTable(appointments);
        viewByComboBox.setItems(DataRetriever.getWeekOptions());
    }

    /** populates Appointments Table with all appointments from passed in ObservableList
     *
     * @param apts - the ObservableList containing appointments to be displayed in Appointments table
     */
    void populateAppointmentsTable(ObservableList<Appointment> apts) {
        appointmentTableView.setItems(apts);
        customerNameColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getCustomerName()));
        appointmentTitleColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getTitle()));
        appointmentDescriptionColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getDescription()));
        appointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
        customerContactColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getEmail()));
        appointmentTypeColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getType()));
        appointmentStartColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getStart()));
        appointmentEndColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getEnd()));
    }

}

