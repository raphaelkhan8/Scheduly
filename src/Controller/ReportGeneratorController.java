package Controller;

import Database.DBQuery;
import Model.SessionHandler;
import Utils.DataRetriever;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

public class ReportGeneratorController implements Initializable {

    @FXML
    private Label reportsPageHeader;

    @FXML
    private ComboBox<String> selectReportComboBox;

    @FXML
    private Button generateReportButton;

    @FXML
    private TextArea report1Text;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea report2Text;

    @FXML
    private TextArea report3Text;

    @FXML
    private Label report1HeaderText;

    @FXML
    private Label report2HeaderText;

    @FXML
    private Label report3HeaderText;

    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: change text to match user's language upon initialization
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectReportComboBox.setItems(DataRetriever.getReportTypes());

        cancelButton.setText(userLanguage.getString("cancelButton"));
        reportsPageHeader.setText(userLanguage.getString("reportsPageHeader"));
        selectReportComboBox.promptTextProperty().setValue(userLanguage.getString("selectReportComboBoxText"));
        generateReportButton.setText(userLanguage.getString("reportTableButtonText"));
        report1HeaderText.setText(userLanguage.getString("reportLabel1Text"));
        report2HeaderText.setText(userLanguage.getString("reportLabel2Text"));
        report3HeaderText.setText(userLanguage.getString("reportLabel3Text"));
    }

    /** Change view back to Home Page
     *
     * @param event - the Event that triggers this function call (click Back button)
     * @throws IOException
     */
    @FXML
    private void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** Generate the report based on user's selection
     *
     * @param event - the Event that triggers this function call (click Generate Report button)
     */
    @FXML
    private void generateReportHandler(ActionEvent event) {
        if(selectReportComboBox.getSelectionModel().getSelectedIndex() == 0) {
            generateFirstReport();
        }
        if(selectReportComboBox.getSelectionModel().getSelectedIndex() == 1) {
            generateSecondReport();
        }
        if(selectReportComboBox.getSelectionModel().getSelectedIndex() == 2) {
            generateThirdReport();
        }
    }

    /** Generates a report on appointments organized by type and month
     *
     */
    private void generateFirstReport() {
        try {
            DBQuery.makeQuery("SELECT Type AS t, MONTHNAME(Start) AS m, COUNT(*) AS total FROM appointments GROUP BY m, t");
            ResultSet appointmentQueryResults = DBQuery.getResult();

            StringBuilder parseReport = new StringBuilder();

            parseReport.append(String.format("%1$-60s %2$-60s %3$s \n", "Month", "Appointment Type", "Total"));
            parseReport.append(String.join("", Collections.nCopies(150, "-")));
            parseReport.append("\n");

            while(appointmentQueryResults.next()) {
                parseReport.append(String.format("%1$-60s %2$-60s %3$s \n", appointmentQueryResults.getString("m"), appointmentQueryResults.getString("t"), appointmentQueryResults.getInt("total")));
            }
            report1Text.setText(parseReport.toString());
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Generates a report on appointments organized by contact
     *
     */
    private void generateSecondReport() {
        try {
            DBQuery.makeQuery("SELECT Contact_ID, Title, Type, Description, Start, End, Customer_ID " +
                    "FROM appointments ORDER BY Contact_ID, MONTH(Start), Start");

            ResultSet appointmentQueryResults = DBQuery.getResult();

            StringBuilder parseReport = new StringBuilder();
            parseReport.append(String.format("%1$-35s %2$-35s %3$-35s %4$-35s %5$-35s %6$-35s %7$s \n", "Contact ID", "Title", "Type", "Description", "Start", "End", "Customer Id"));
            parseReport.append(String.join("", Collections.nCopies(150, "-")));
            parseReport.append("\n");

            while(appointmentQueryResults.next()) {

                parseReport.append(String.format("%1$-35s %2$-35s %3$-35s %4$-35s %5$-35s %6$-35s %7$s \n",
                        appointmentQueryResults.getString("Contact_ID"), appointmentQueryResults.getString("Title"),
                        appointmentQueryResults.getString("Type"), appointmentQueryResults.getString("Description"),
                        appointmentQueryResults.getString("Start"), appointmentQueryResults.getString("End"),
                        appointmentQueryResults.getString("Customer_ID")));
            }
            report2Text.setText(parseReport.toString());
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Generates a report ranking customers by number of appointments
     *
     */
    private void generateThirdReport() {
        try {
            DBQuery.makeQuery("SELECT a.Customer_ID, c.Customer_Name AS Name, COUNT(*) AS Count FROM appointments a " +
                    "JOIN customers c ON a.Customer_ID = c.Customer_ID GROUP BY Customer_ID DESC;");

            ResultSet appointmentQueryResults = DBQuery.getResult();

            StringBuilder parseReport = new StringBuilder();
            parseReport.append(String.format("%1$-60s %2$-60s %3$s \n", "Customer ID ", "Customer Name", "Number of Appointments"));
            parseReport.append(String.join("", Collections.nCopies(150, "-")));
            parseReport.append("\n");

            while(appointmentQueryResults.next()) {

                parseReport.append(String.format("%1$-80s %2$-80s %3$s \n", appointmentQueryResults.getString(1),
                        appointmentQueryResults.getString(2), appointmentQueryResults.getString(3)));
            }
            report3Text.setText(parseReport.toString());
        }
        catch(SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

