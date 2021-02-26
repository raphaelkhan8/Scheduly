package Controller;

import Database.DBQuery;
import Model.SessionHandler;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ComboBox<String> addCustomerCountryComboBox;

    @FXML
    private ComboBox<String> addCustomerDivisionComboBox;

    @FXML
    private Button saveCustomerButton;

    @FXML
    private Button cancelButton;

    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();
    /** container to hold countries in database */
    ObservableList<String> countryList = FXCollections.observableArrayList();
    /** container to hold first-level divisions */
    ObservableList<String> divisionList = FXCollections.observableArrayList();
    /** container to hold selected-country's Country_ID */
    int selectedCountryId;
    /** container to hold selected-division's Division_ID */
    int selectedDivisionId;

    /** change text to match user's language and populate country combo box upon initialization */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateCountryComboBox();
            addCustomerCountryComboBox.setItems(countryList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

    /** records the customer's selected country and populates division combo box with associated divisions */
    @FXML
    void addCustomerCountryHandler(ActionEvent event) throws SQLException {
        selectedCountryId = addCustomerCountryComboBox.getSelectionModel().getSelectedIndex() + 1;
        populateDivisionComboBox(selectedCountryId);
        if (divisionList.size() == 0) {
            ObservableList<String> empty = FXCollections.observableArrayList();
            empty.add(userLanguage.getString("noCountryDivisions"));
            addCustomerDivisionComboBox.setItems(empty);
        } else {
            addCustomerDivisionComboBox.setItems(divisionList);
        }
    }

    /** Records the customer's selected division */
    @FXML
    void addCustomerDivisionHandler(ActionEvent event) {
        selectedDivisionId = addCustomerDivisionComboBox.getSelectionModel().getSelectedIndex() + 1;
    }

    /** Cancel button returns view to Customers Table page */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** Method for saving new customer data to database */
    @FXML
    void saveCustomerHandler(ActionEvent event) {

    }

    /** populates Country combo-box (drop-down) with countries stored in database */
    @FXML
    void populateCountryComboBox() throws SQLException {
        DBQuery.makeQuery("SELECT Country FROM countries");
        ResultSet countries = DBQuery.getResult();
        while (countries.next()) {
            countryList.add(countries.getString(1));
        }
    }

    /** populates Division combo-box (drop-down) with selected countries divisions */
    @FXML
    void populateDivisionComboBox(int countryId) throws SQLException {
        DBQuery.makeQuery("SELECT Division from first_level_divisions WHERE Country_ID = " + countryId);
        ResultSet divisions = DBQuery.getResult();
        while (divisions.next()) {
            divisionList.add(divisions.getString(1));
        }
    }

}
