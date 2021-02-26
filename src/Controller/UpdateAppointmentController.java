package Controller;

import Model.SessionHandler;
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
    private ComboBox<?> startTimeComboBox;

    @FXML
    private DatePicker updateAppointmentDatePicker;

    @FXML
    private ComboBox<?> locationComboBox;

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
    private ComboBox<?> assignedContactComboBox;

    @FXML
    private Button saveAppointmentButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<?> updateCustomerTableView;

    @FXML
    private TableColumn<?, ?> updateAppointmentCustomerIDColumn;

    @FXML
    private TableColumn<?, ?> updateAppointmentIDColumn;

    @FXML
    private TableColumn<?, ?> updateAppointmentLocationColumn;

    @FXML
    private TableColumn<?, ?> updateAppointmentLocalDateColumn;

    @FXML
    private TableColumn<?, ?> updateAppointmentUTCDateColumn;

    @FXML
    private Label updateAppointmentTableHeaderText;

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        assignedContactLabel.setText(userLanguage.getString("AssignedContact"));
        dateLabel.setText(userLanguage.getString("Date"));
        startTimeLabel.setText(userLanguage.getString("StartTime"));
        endTimeLabel.setText(userLanguage.getString("EndTime"));
        updateAppointmentIDColumn.setText(userLanguage.getString("AppointmentID"));
        updateAppointmentCustomerIDColumn.setText(userLanguage.getString("CustomerID"));
        updateAppointmentLocationColumn.setText(userLanguage.getString("Location"));
        updateAppointmentLocalDateColumn.setText(userLanguage.getString("LocalStartTime"));
        updateAppointmentUTCDateColumn.setText(userLanguage.getString("UTCStartTime"));
    }

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

}

