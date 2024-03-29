package Controller;

import Model.SessionHandler;
import Utils.AlertMessages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomePageController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label optionsLabel;

    @FXML
    private Label customerLabel;

    @FXML
    private Label appointmentLabel;

    @FXML
    private Label reportLabel;

    @FXML
    private Button customerTableButton;

    @FXML
    private Button appointmentTableButton;

    @FXML
    private Label customerLabelText;

    @FXML
    private Label appointmentLabelText;

    @FXML
    private Button logoutButton;

    @FXML
    private Button reportGeneratorButton;

    @FXML
    private Label reportLabelText;

    @FXML
    private Label reportLabel1;

    @FXML
    private Label reportLabel2;

    @FXML
    private Label reportLabel3;

    /** container to hold user's language */
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    /** Initialization Override: change text to match user's language upon initialization */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcomeLabel.setText(userLanguage.getString("welcomeLabelMessage"));
        optionsLabel.setText(userLanguage.getString("optionsLabelMessage"));
        customerLabel.setText(userLanguage.getString("customerLabel"));
        appointmentLabel.setText(userLanguage.getString("appointmentLabel"));
        reportLabel.setText(userLanguage.getString("reportLabel"));
        customerLabelText.setText(userLanguage.getString("customerLabelText"));
        appointmentLabelText.setText(userLanguage.getString("appointmentLabelText"));
        reportLabelText.setText(userLanguage.getString("reportLabelText"));
        reportLabel1.setText("1. " + userLanguage.getString("reportLabel1Text"));
        reportLabel2.setText("2. " + userLanguage.getString("reportLabel2Text"));
        reportLabel3.setText("3. " + userLanguage.getString("reportLabel3Text"));
        customerTableButton.setText(userLanguage.getString("customerTableButtonText"));
        appointmentTableButton.setText(userLanguage.getString("appointmentTableButtonText"));
        reportGeneratorButton.setText(userLanguage.getString("reportTableButtonText"));
        logoutButton.setText(userLanguage.getString("logoutButton"));
    }

    /** clear loggedInUser variable and change view back to Login page
     *
     * @param event - the event that triggers this function call (click Logout button)
     * @throws IOException
     */
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        AtomicBoolean logout = AlertMessages.confirmMessage(userLanguage.getString("logoutConfirmMsg"));
        if (logout.get()) {
            LoginController.clearCurrentUser();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } else {
            return;
        }
    }

    /** changes view to the Appointment Manager page
     *
     * @param event - the Event that triggers this function call (click View Appointment List button)
     * @throws IOException
     */
    @FXML
    private void openAptManagerView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** changes view to the Customers Table page
     *
     * @param event - the Event that triggers this function call (click View Customer List button)
     * @throws IOException
     */
    @FXML
    private void openCustomerTableView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** changes view to the Report Generator page
     *
     * @param event - the Event that triggers this function call (click Generate Report button)
     * @throws IOException
     */
    @FXML
    private void openReportGeneratorView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/ReportGenerator.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}

