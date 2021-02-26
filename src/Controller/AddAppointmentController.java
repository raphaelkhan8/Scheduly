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
    private TableView<?> addCustomerTableView;

    @FXML
    private TableColumn<?, ?> addAppointmentCustomerIDColumn;

    @FXML
    private TableColumn<?, ?> addAppointmentIDColumn;

    @FXML
    private TableColumn<?, ?> addAppointmentLocationColumn;

    @FXML
    private TableColumn<?, ?> addAppointmentLocalDateColumn;

    @FXML
    private TableColumn<?, ?> addAppointmentUTCDateColumn;

    @FXML
    private Label addAppointmentTableHeaderText;

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelButton.setText(userLanguage.getString("cancelButton"));
    }

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

}

