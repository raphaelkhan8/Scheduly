package Controller;

import Model.SessionHandler;
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
    private TableView<?> appointmentTableView;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> customerContactColumn;

    @FXML
    private TableColumn<?, ?> appointmentTitleColumn;

    @FXML
    private TableColumn<?, ?> appointmentTypeColumn;

    @FXML
    private TableColumn<?, ?> appointmentLocationColumn;

    @FXML
    private TableColumn<?, ?> appointmentDescriptionColumn;

    @FXML
    private TableColumn<?, ?> appointmentStartColumn;

    @FXML
    private TableColumn<?, ?> appointmentEndColumn;

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

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        customerContactColumn.setText(userLanguage.getString("AssignedContact"));
        appointmentTitleColumn.setText(userLanguage.getString("Title"));
        appointmentTypeColumn.setText(userLanguage.getString("Type"));
        appointmentLocationColumn.setText(userLanguage.getString("Location"));
        appointmentDescriptionColumn.setText(userLanguage.getString("Description"));
        appointmentStartColumn.setText(userLanguage.getString("StartTime"));
        appointmentEndColumn.setText(userLanguage.getString("EndTime"));
    }

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
    void updateAppointmentHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/UpdateAppointment.fxml"));
        stage.setScene(new Scene((Parent) scene));
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

}

