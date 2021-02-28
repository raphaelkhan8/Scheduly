package Controller;

import Database.DBQuery;
import Model.SessionHandler;
import Utils.AlertMessages;
import Utils.DataRetriever;
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
    /** container to hold first-level division IDs */
    ObservableList<String> divisionIDList = FXCollections.observableArrayList();
    /** container to hold first-level division names */
    ObservableList<String> divisionNameList = FXCollections.observableArrayList();
    /** container to hold selected-country's Country_ID */
    int selectedCountryId = -1;
    /** container to hold selected-division's index in combo-box */
    int selectedDivisionIndex = -1;

    /** Initialization Override: change text to match user's language and populate country combo box
     *
     */
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

    /** Records the customer's selected country and populates division combo box with associated divisions
     *
     * @param event - the event that triggers this function call (select a country from country combo-box)
     * @throws SQLException
     */
    @FXML
    void addCustomerCountryHandler(ActionEvent event) throws SQLException {
        selectedCountryId = addCustomerCountryComboBox.getSelectionModel().getSelectedIndex();
        populateDivisionComboBox(selectedCountryId);
        if (divisionNameList.size() == 0) {
            ObservableList<String> empty = FXCollections.observableArrayList();
            empty.add(userLanguage.getString("noCountryDivisions"));
            addCustomerDivisionComboBox.setItems(empty);
        } else {
            addCustomerDivisionComboBox.setItems(divisionNameList);
        }
    }

    /** Records the customer's selected division
     *
     * @param event - the event that triggers this function call (select a division from division combo-box)
     */
    @FXML
    void addCustomerDivisionHandler(ActionEvent event) {
        selectedDivisionIndex = addCustomerDivisionComboBox.getSelectionModel().getSelectedIndex();
    }

    /** Cancel button returns view to Customers Table page
     *
     * @param event - the event that triggers this function call (click Cancel button)
     * @throws IOException
     */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** Method for saving new customer data to database
     *
     * @param event - the event that triggers this function call (click Save button)
     */
    @FXML
    void saveCustomerHandler(ActionEvent event) {
        // get user's text input
        int customerId = 1;
        String customerName = addCustomerNameText.getText();
        String address = addCustomerAddressText.getText();
        String postalCode = addCustomerPostalText.getText();
        String phone = addCustomerPhoneText.getText();
        // verify that all text fields were filled out
        if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingFieldMessage"));
            return;
        }
        // verify that the country and division drop-boxes were selected
        if (selectedCountryId < 0 || selectedDivisionIndex < 0) {
            AlertMessages.errorMessage(userLanguage.getString("selectCountryDivisionMsg"));
            return;
        }
        // if all info is filled-out:
        try {
            System.out.println(selectedDivisionIndex);
            // get the selected country/division's Division_ID from the first-level-divisions table (foreign key)
            int selectedDivisionId = Integer.parseInt(divisionIDList.get(selectedDivisionIndex));
            // get last customerId in database and increment it by one for new customerId
            DBQuery.makeQuery("SELECT MAX(Customer_ID) FROM customers");
            ResultSet rs = DBQuery.getResult();
            if(rs.next()) {
                customerId = rs.getInt(1);
                customerId++;
            }
            // Then, save the customer info to the database and alert user of successful save
            DBQuery.makeQuery("INSERT INTO customers SET Customer_ID=" + customerId + ", Customer_Name='" +
                    customerName + "', Address='" + address + "', Postal_Code='" + postalCode + "', Phone='" + phone +
                    "', Create_Date=NOW(), Created_By='', Last_Update=NOW(), Last_Updated_By='', Division_ID=" + selectedDivisionId);
            AlertMessages.alertMessage(userLanguage.getString("customerAddedMessage"));
            // Afterwards, go back to Customer Table view
            cancelView(event);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            AlertMessages.errorMessage(userLanguage.getString("customerAddErrorMessage"));
        }
    }

    /** Populates Country combo-box (drop-down) with countries stored in database
     *
     * @throws SQLException
     */
    void populateCountryComboBox() throws SQLException {
        ResultSet countries = DataRetriever.getAllCountries();
        while (countries.next()) {
            countryList.add(countries.getString(2));
        }
    }

    /** Populates Division combo-box (drop-down) with selected countries divisions
     *
     * @param countryId - the int corresponding to the selected country's Id
     * @throws SQLException
     */
    void populateDivisionComboBox(int countryId) throws SQLException {
        divisionIDList.clear();
        divisionNameList.clear();
        ResultSet divisions = DataRetriever.getDivisionInfo(countryId + 1);
        while (divisions.next()) {
            divisionIDList.add(divisions.getString(1));
            divisionNameList.add(divisions.getString(2));
        }
    }

}
