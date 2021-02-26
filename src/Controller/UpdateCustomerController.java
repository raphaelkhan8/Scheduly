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

public class UpdateCustomerController implements Initializable {

    @FXML
    private Label updateCustomerHeader;

    @FXML
    private Label updateCustomerHeaderText;

    @FXML
    private Label updateCustomerNameLabel;

    @FXML
    private Label updateCustomerAddressLabel;

    @FXML
    private Label updateCustomerCountryLabel;

    @FXML
    private Label updateCustomerDivisionLabel;

    @FXML
    private Label updateCustomerPostalLabel;

    @FXML
    private Label updateCustomerPhoneLabel;

    @FXML
    private TextField updateCustomerNameText;

    @FXML
    private TextField updateCustomerAddressText;

    @FXML
    private TextField updateCustomerPostalText;

    @FXML
    private TextField updateCustomerPhoneText;

    @FXML
    private ComboBox<?> updateCustomerCountryComboBox;

    @FXML
    private ComboBox<?> updateCustomerDivisionComboBox;

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
        updateCustomerHeader.setText(userLanguage.getString("updateCustomerHeader"));
        updateCustomerHeaderText.setText(userLanguage.getString("updateCustomerHeaderText"));
        updateCustomerNameLabel.setText(userLanguage.getString("Name"));
        updateCustomerAddressLabel.setText(userLanguage.getString("Address"));
        updateCustomerCountryLabel.setText(userLanguage.getString("Country"));
        updateCustomerDivisionLabel.setText(userLanguage.getString("Division"));
        updateCustomerPostalLabel.setText(userLanguage.getString("Postal_Code"));
        updateCustomerPhoneLabel.setText(userLanguage.getString("Phone"));
        updateCustomerCountryComboBox.promptTextProperty().setValue(userLanguage.getString("selectCountry"));
        updateCustomerDivisionComboBox.promptTextProperty().setValue(userLanguage.getString("selectDivision"));
    }

    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void updateCustomerCountryHandler(ActionEvent event) {

    }

    @FXML
    void updateCustomerDivisionHandler(ActionEvent event) {

    }

    @FXML
    void updateCustomerHandler(ActionEvent event) {

    }

}

