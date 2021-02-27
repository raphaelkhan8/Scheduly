package Controller;

import Database.DBQuery;
import Model.Customer;
import Model.SessionHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    /** var to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** populate customer's table with customer data from the database
     *  and change text to match user's language upon view initialization  */
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

    /** changes view to Add Appointment page */
    @FXML
    void addAppointmentHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** changes view to Add Customer page */
    @FXML
    void addCustomerHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** deletes selected customer from database */
    @FXML
    void deleteCustomerHandler(ActionEvent event) {

    }

    /** changes view to Update Customer page */
    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/UpdateCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** changes view to Home Page */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** loads customer data from database into the Customer TableView and displays each customer as a table-row */
    @FXML
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
            Customer newCustomer = new Customer(customerId, customerName, address, postalCode, phone, country, division);
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
