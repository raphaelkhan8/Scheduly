package Controller;

import Database.DBQuery;
import Model.Customer;
import Model.SessionHandler;
import Utils.DataRetriever;
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
    private ComboBox<String> updateCustomerCountryComboBox;

    @FXML
    private ComboBox<String> updateCustomerDivisionComboBox;

    @FXML
    private Button saveCustomerButton;

    @FXML
    private Button cancelButton;

    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();
    /** container to hold country names for displaying in country combo-box */
    ObservableList<String> countryList = FXCollections.observableArrayList();
    /** container to hold first-level division IDs */
    ObservableList<String> divisionIDList = FXCollections.observableArrayList();
    /** container to hold first-level division names */
    ObservableList<String> divisionNameList = FXCollections.observableArrayList();
    /** container to hold selected customer from previous view */
    Customer selectedCustomer;
    /** container to hold selected customer's countryId */
    int selectedCountryId;

    /** Initialization Override: Populate country combo-box with country names from database and
     * changes text to match user's language
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateCountryComboBox();
            updateCustomerCountryComboBox.setItems(countryList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

    /** Changes view to Customers Table page
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

    /** Records the customer's selected country and populates division combo box with associated divisions
     *
     * @throws SQLException
     */
    @FXML
    void updateCustomerCountryHandler() throws SQLException {
        selectedCountryId = updateCustomerCountryComboBox.getSelectionModel().getSelectedIndex() + 1;
        populateDivisionComboBox(selectedCountryId);
        if (divisionNameList.size() == 0) {
            ObservableList<String> empty = FXCollections.observableArrayList();
            empty.add(userLanguage.getString("noCountryDivisions"));
            updateCustomerDivisionComboBox.setItems(empty);
        } else {
            updateCustomerDivisionComboBox.setItems(divisionNameList);
        }
    }

    /** Records the customer's selected country and populates division combo box with associated divisions
     *
     * @throws SQLException
     */
    @FXML
    void updateCustomerDivisionHandler() throws SQLException {
        DBQuery.makeQuery("SELECT d.Division_ID, d.Division from first_level_divisions AS d INNER JOIN countries AS c ON d.Country_ID = " + selectedCountryId);
        ResultSet divisions = DBQuery.getResult();
        while (divisions.next()) {
            divisionIDList.add(divisions.getString(1));
            divisionNameList.add(divisions.getString(2));
        }
        this.updateCustomerDivisionComboBox.setItems(divisionNameList);
    }

    /** Records the customer's updated division
     *
     * @param event - the event that triggers this function call (click Update button)
     */
    @FXML
    void updateCustomerHandler(ActionEvent event) {

    }

    /** Gets selected customer from CustomerTable view to populates UpdateCustomer view fields
     *
     * @param customer - the Customer object selected to be updated
     * @param countryId - the int corresponding to the selected customer's countryId
     * @throws SQLException
     */
    public void getSelectedCustomer(Customer customer, int countryId) throws SQLException {

        selectedCustomer = customer;
        selectedCountryId = countryId;

        Customer selectedCustomer = (Customer) customer;

        this.updateCustomerNameText.setText(selectedCustomer.getUsername());
        this.updateCustomerAddressText.setText((selectedCustomer.getAddress()));
        this.updateCustomerCountryComboBox.promptTextProperty().setValue(selectedCustomer.getCountry());
        this.updateCustomerDivisionComboBox.promptTextProperty().setValue(selectedCustomer.getDivision());
        this.updateCustomerPostalText.setText((selectedCustomer.getPostalCode()));
        this.updateCustomerPhoneText.setText((selectedCustomer.getPhone()));

        populateDivisionComboBox(countryId);
        updateCustomerDivisionComboBox.setItems(divisionNameList);

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
        ResultSet divisions = DataRetriever.getDivisionInfo(countryId);
        while (divisions.next()) {
            divisionIDList.add(divisions.getString(1));
            divisionNameList.add(divisions.getString(2));
        }
    }

}

