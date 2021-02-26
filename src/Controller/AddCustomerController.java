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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private Label addCustomerHeader;

    @FXML
    private Label addCustomerHeaderText;

    @FXML
    private Label addCustomerNameLabel;

    @FXML
    private Label addCustomerAddressLabel;

    @FXML
    private Label addCustomerCountryLabel;

    @FXML
    private Label addCustomerDivisionLabel;

    @FXML
    private Label addCustomerPostalLabel;

    @FXML
    private Label addCustomerPhoneLabel;

    @FXML
    private TextField addCustomerNameText;

    @FXML
    private TextField addCustomerAddressText;

    @FXML
    private TextField addCustomerPostalText;

    @FXML
    private TextField addCustomerPhoneText;

    @FXML
    private ComboBox<?> addCustomerCountryComboBox;

    @FXML
    private ComboBox<?> addCustomerDivisionComboBox;

    @FXML
    private Button saveCustomerButton;

    @FXML
    private Button cancelButton;

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelButton.setText(userLanguage.getString("cancelButton"));
        saveCustomerButton.setText(userLanguage.getString("saveButton"));
        addCustomerHeader.setText(userLanguage.getString("addCustomerHeader"));
        addCustomerHeaderText.setText(userLanguage.getString("addCustomerHeaderText"));
        addCustomerNameLabel.setText(userLanguage.getString("Name"));
        addCustomerAddressLabel.setText(userLanguage.getString("Address"));
        addCustomerCountryLabel.setText(userLanguage.getString("Country"));
        addCustomerDivisionLabel.setText(userLanguage.getString("Division"));
        addCustomerPostalLabel.setText(userLanguage.getString("Postal_Code"));
        addCustomerPhoneLabel.setText(userLanguage.getString("Phone"));
        addCustomerCountryComboBox.promptTextProperty().setValue(userLanguage.getString("selectCountry"));
        addCustomerDivisionComboBox.promptTextProperty().setValue(userLanguage.getString("selectDivision"));
    }

    @FXML
    void addCustomerCountryHandler(ActionEvent event) {

    }

    @FXML
    void addCustomerDivisionHandler(ActionEvent event) {

    }

    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void saveCustomerHandler(ActionEvent event) {

    }

}
