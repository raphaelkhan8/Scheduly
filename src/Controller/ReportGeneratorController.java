package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportGeneratorController {

    @FXML
    private Label ReportsPageHeader;

    @FXML
    private Label ReportsPageHeaderText;

    @FXML
    private ComboBox<?> selectReportComboBox;

    @FXML
    private Button generateReportButton;

    @FXML
    private TextArea Report1Text;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea Report2Text;

    @FXML
    private TextArea Report3Text;

    @FXML
    private Label Report1HeaderText;

    @FXML
    private Label Report2HeaderText;

    @FXML
    private Label Report3HeaderText;

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

