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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField addCustomerText;

    @FXML
    private TextField addCustomerAddressText;

    @FXML
    private TextField addCustomerPostalCodeText;

    @FXML
    private TextField addCustomPhoneText;

    @FXML
    private ComboBox<?> addCustomerCityComboBox;

    @FXML
    private ComboBox<?> addCustomerCountryComboBox;

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
    }

    @FXML
    void addCustomerCityHandler(ActionEvent event) {

    }

    @FXML
    void addCustomerCountryHandler(ActionEvent event) {

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
