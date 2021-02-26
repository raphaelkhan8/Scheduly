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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportGeneratorController implements Initializable {

    @FXML
    private Label reportsPageHeader;

    @FXML
    private Label reportsPageHeaderText;

    @FXML
    private ComboBox<?> selectReportComboBox;

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

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancelButton.setText(userLanguage.getString("cancelButton"));
        reportsPageHeader.setText(userLanguage.getString("reportsPageHeader"));
        reportsPageHeaderText.setText(userLanguage.getString("reportsPageHeaderText"));
        generateReportButton.setText(userLanguage.getString("reportTableButtonText"));
        report1HeaderText.setText(userLanguage.getString("reportLabel1Text"));
        report2HeaderText.setText(userLanguage.getString("reportLabel2Text"));
        report3HeaderText.setText(userLanguage.getString("reportLabel3Text"));
    }

    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void generateReportHandler(ActionEvent event) {

    }

}

