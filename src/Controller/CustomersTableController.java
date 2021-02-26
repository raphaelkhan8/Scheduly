package Controller;

import Model.SessionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomersTableController implements Initializable {

    @FXML
    private Label customersTableHeader;

    @FXML
    private TableView<?> customersTableView;

    @FXML
    private TableColumn<?, ?> customerIDTable;

    @FXML
    private TableColumn<?, ?> customerNameTable;

    @FXML
    private TableColumn<?, ?> customerAddressTable;

    @FXML
    private TableColumn<?, ?> customerCityTable;

    @FXML
    private TableColumn<?, ?> customerCountryTable;

    @FXML
    private TableColumn<?, ?> customerPostalCodeTable;

    @FXML
    private TableColumn<?, ?> customerPhoneTable;

    @FXML
    private Button customersTableAddButton;

    @FXML
    private Button customersTableUpdateButton;

    @FXML
    private Button customersTableDeleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label customersTableButtonMessage;

    @FXML
    private Label addAppointmentButtonMessage;

    @FXML
    private Button appointmentsTableAddButton;

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customersTableHeader.setText(userLanguage.getString("customersTableHeader"));
        customersTableButtonMessage.setText(userLanguage.getString("customersTableButtonMessage"));
        addAppointmentButtonMessage.setText(userLanguage.getString("addAppointmentButtonMessage"));
        appointmentsTableAddButton.setText(userLanguage.getString("appointmentsTableAddButton"));
        customersTableAddButton.setText(userLanguage.getString("addButton"));
        customersTableUpdateButton.setText(userLanguage.getString("updateButton"));
        customersTableDeleteButton.setText(userLanguage.getString("deleteButton"));
        cancelButton.setText(userLanguage.getString("cancelButton"));
        customerIDTable.setText(userLanguage.getString("ID"));
        customerNameTable.setText(userLanguage.getString("Name"));
        customerAddressTable.setText(userLanguage.getString("Address"));
        customerCityTable.setText(userLanguage.getString("City"));
        customerCountryTable.setText(userLanguage.getString("Country"));
        customerPostalCodeTable.setText(userLanguage.getString("Postal_Code"));
        customerPhoneTable.setText(userLanguage.getString("Phone"));
    }

    @FXML
    void addAppointmentHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void addCustomerHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void deleteCustomerHandler(ActionEvent event) {

    }

    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/UpdateCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}
