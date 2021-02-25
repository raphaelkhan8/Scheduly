package Controller;

import Model.SessionHandler;
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
    private Label reportLabel1;

    @FXML
    private Label reportLabel2;

    @FXML
    private Label reportLabel3;

    @FXML
    private Label reportLabel4;

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        welcomeLabel.setText(userLanguage.getString("welcomeLabelMessage"));
        optionsLabel.setText(userLanguage.getString("optionsLabelMessage"));
        customerLabel.setText(userLanguage.getString("customerLabel"));
        appointmentLabel.setText(userLanguage.getString("appointmentLabel"));
        reportLabel.setText(userLanguage.getString("reportLabel"));
        customerLabelText.setText(userLanguage.getString("customerLabelText"));
        appointmentLabelText.setText(userLanguage.getString("appointmentLabelText"));
        reportLabel1.setText(userLanguage.getString("reportLabel1Text"));
        reportLabel2.setText(userLanguage.getString("reportLabel2Text"));
        reportLabel3.setText(userLanguage.getString("reportLabel3Text"));
        reportLabel4.setText(userLanguage.getString("reportLabel4Text"));
        customerTableButton.setText(userLanguage.getString("customerTableButtonText"));
        appointmentTableButton.setText(userLanguage.getString("appointmentTableButtonText"));
        reportGeneratorButton.setText(userLanguage.getString("reportTableButtonText"));
        logoutButton.setText(userLanguage.getString("logoutButton"));
    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    @FXML
    void openAptManagerView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void openCustomerTableView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/CustomersTable.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void openReportGeneratorView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/ReportGenerator.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}

