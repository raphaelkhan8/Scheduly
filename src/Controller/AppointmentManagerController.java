package Controller;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    private ComboBox<?> viewByComboBox;

    @FXML
    private Button searchTableSorterButton;

    /** containers containing View By options */
    ObservableList<String> monthOptions = FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December");
    ObservableList<String> weekOptions = FXCollections.observableArrayList("Last Week", "This Week", "Next Week");

    /** container to hold selected appointment */
    private Appointment selectedAppointment;

    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: Populate table with all appointments and change text to match user's language */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateAppointmentsTable();
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

    @FXML
    void deleteAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void updateAppointmentHandler(ActionEvent event) throws IOException, SQLException {
//        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
//        Object scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
//        stage.setScene(new Scene((Parent) scene));
//        stage.show();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
        loader.load();
        UpdateAppointmentController controller = loader.getController();
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();

        controller.getSelectedAppointment(selectedAppointment);

        Stage stage = (Stage) updateAppointmentButton.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void searchTableSorterHandler(ActionEvent event) {

    }

    @FXML
    void viewByMonthHandler(ActionEvent event) {

    }

    @FXML
    void viewByWeekHandler(ActionEvent event) {

    }

    void populateAppointmentsTable() throws SQLException {
        ObservableList<Appointment> apts = Appointment.getAppointments(-1);
        appointmentTableView.setItems(apts);
        customerNameColumn.setCellValueFactory(apt -> new SimpleStringProperty(Customer.getCustomerName(apt.getValue().getCustomerId())));
        appointmentTitleColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getTitle()));
        appointmentDescriptionColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getDescription()));
        appointmentLocationColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getLocation()));
        customerContactColumn.setCellValueFactory(apt -> new SimpleStringProperty(Contact.getEmail(apt.getValue().getContactId()).getEmail()));
        appointmentTypeColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getType()));
        appointmentStartColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getStart()));
        appointmentEndColumn.setCellValueFactory(apt -> new SimpleStringProperty(apt.getValue().getEnd()));
    }

}

