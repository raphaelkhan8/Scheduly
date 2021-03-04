package Controller;

import Database.DBQuery;
import Model.Country;
import Model.Customer;
import Model.SessionHandler;
import Utils.AlertMessages;
import Utils.DataRetriever;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomersTableController implements Initializable {

    @FXML
    private Label customersTableHeader;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TableColumn<Customer, String> customerIDTable;

    @FXML
    private TableColumn<Customer, String> customerNameTable;

    @FXML
    private TableColumn<Customer, String> customerAddressTable;

    @FXML
    private TableColumn<Customer, String> customerDivisionTable;

    @FXML
    private TableColumn<Customer, String> customerCountryTable;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeTable;

    @FXML
    private TableColumn<Customer, String> customerPhoneTable;

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

    /** container for customer data */
    ObservableList<Customer> customersList = FXCollections.observableArrayList();

    /** container to hold selected Customer */
    private Customer selectedCustomer;

    /** container to hold selected-country's Country_ID */
    int selectedCountryId = 0;

    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: Populate customer's table with customer data from the database
     *  and change text to match user's language
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populateCustomersTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
        customerDivisionTable.setText(userLanguage.getString("Division"));
        customerCountryTable.setText(userLanguage.getString("Country"));
        customerPostalCodeTable.setText(userLanguage.getString("Postal_Code"));
        customerPhoneTable.setText(userLanguage.getString("Phone"));
    }

    /** Changes view to Add Appointment page
     *
     * @param event - the event that triggers this function call (click Add Appointment button)
     * @throws IOException
     */
    @FXML
    void addAppointmentHandler(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/AddAppointment.fxml"));
        loader.load();
        AddAppointmentController controller = loader.getController();
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        // alert user if a customer is not selected
        if (selectedCustomer == null) {
            AlertMessages.errorMessage(userLanguage.getString("addAppointmentNoCustomerMessage"));
            return;
        }
        controller.getSelectedCustomer(selectedCustomer);
        Stage stage = (Stage) customersTableUpdateButton.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Changes view to Add Customer page
     *
     * @param event - the event that triggers this function call (click Add button)
     * @throws IOException
     */
    @FXML
    void addCustomerHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** Deletes selected customer from database
     *
     * @param event - the event that triggers this function call (click Delete button)
     */
    @FXML
    void deleteCustomerHandler(ActionEvent event) {
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            AlertMessages.errorMessage(userLanguage.getString("deleteCustomerNoCustomerMessage"));
            return;
        }

        AtomicBoolean proceed = AlertMessages.confirmMessage(userLanguage.getString("customerDeleteConfirmMessage"));
        if (proceed.get() == true) {

            int numberOfCustomers = customersTableView.getItems().size();
            int selectedID = selectedCustomer.getCustomerId();

            if (numberOfCustomers == 1) {
                customersTableView.getItems().remove(0);
            } else {
                ObservableList<Customer> allCustomers, singleCustomer;
                allCustomers = customersTableView.getItems();
                singleCustomer = customersTableView.getSelectionModel().getSelectedItems();
                singleCustomer.forEach(allCustomers::remove);
                selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
            }

            try {
                // first delete all of the customer's associated appointments
                DBQuery.makeQuery("DELETE FROM appointments WHERE Customer_ID =" + selectedID);
                // then, delete the customer from the database
                DBQuery.makeQuery("DELETE FROM customers WHERE Customer_ID =" + selectedID);
                AlertMessages.alertMessage(userLanguage.getString("customerDeleteSuccessMessage"));
            }
            catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /** Changes view to Update Customer page with selected Customer
     *
     * @param event - the event that triggers this function call (click Update button)
     * @throws IOException
     */
    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UpdateCustomer.fxml"));
        loader.load();
        UpdateCustomerController controller = loader.getController();
        selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            AlertMessages.errorMessage(userLanguage.getString("updateCustomerNoCustomerMessage"));
            return;
        }

        try {
            String selectedCountry = selectedCustomer.getCountry();
            selectedCountryId = Country.getCountryId(selectedCountry);
            controller.getSelectedCustomer(selectedCustomer, selectedCountryId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Stage stage = (Stage) customersTableUpdateButton.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Cancel button changes view to Home Page
     *
     * @param event - - the event that triggers this function call (click Cancel button)
     * @throws IOException
     */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** Loads customer data from database into the Customer TableView and displays each customer as a table-row
     *
     * @throws SQLException
     */
    void populateCustomersTable() throws SQLException {
        DBQuery.makeQuery("SELECT cust.*, f.Division, c.Country FROM customers AS cust JOIN first_level_divisions AS f ON f.Division_ID = cust.Division_ID JOIN countries AS c ON c.Country_ID = f.Country_ID");
        ResultSet customers = DBQuery.getResult();
        while (customers.next()) {
            int customerId = Integer.parseInt(customers.getString("Customer_ID"));
            int divisionId = Integer.parseInt(customers.getString("Division_ID"));
            String customerName = customers.getString("Customer_Name");
            String address = customers.getString("Address");
            String postalCode = customers.getString("Postal_Code");
            String phone = customers.getString("Phone");
            String division = customers.getString("Division");
            String country = customers.getString("Country");
            Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phone, country, division, divisionId);
            customersList.add(newCustomer);
        }
        customersTableView.setItems(customersList);
        customerIDTable.setCellValueFactory(c -> new SimpleStringProperty(Integer.toString(c.getValue().getCustomerId())));
        customerNameTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsername()));
        customerAddressTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress()));
        customerDivisionTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDivision()));
        customerCountryTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCountry()));
        customerPostalCodeTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPostalCode()));
        customerPhoneTable.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhone()));
    }

}
