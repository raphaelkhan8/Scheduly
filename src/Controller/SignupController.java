package Controller;

import Database.DBQuery;
import Model.SessionHandler;
import Utils.AlertMessages;
import com.mysql.cj.log.Log;
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
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextArea signupMessage;

    @FXML
    private Button signupButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea sloganLabel;

    // var to hold user's language
    ResourceBundle userLanguage = SessionHandler.getUserLanguage();

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sloganLabel.setText(userLanguage.getString("sloganLabel"));
        signupMessage.setText(userLanguage.getString("signupMessage"));
        usernameLabel.setText(userLanguage.getString("username"));
        passwordLabel.setText(userLanguage.getString("password"));
        signupButton.setText(userLanguage.getString("signupButton"));
        cancelButton.setText(userLanguage.getString("cancelButton"));
    }

    @FXML
    void handleSignup(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int userId = 1;

        if (username.isEmpty() || password.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingCredential"));
            return;
        }

        boolean proceed = LoginController.checkLoginInfo(username, password);

        if (proceed) {
            AlertMessages.alertMessage(userLanguage.getString("signupRedundant"));
            openHomePage(event);
        }
        else {
            try {
                DBQuery.makeQuery("SELECT MAX(User_ID) FROM users");
                ResultSet rs = DBQuery.getResult();
                if(rs.next()){
                    userId = rs.getInt(1);
                    userId++;
                }
                DBQuery.makeQuery("INSERT INTO users SET User_ID=" + userId + ", User_Name='" + username +
                        "', Password='" + password + "', Create_Date=NOW(), Created_By='', Last_Update=NOW(), Last_Updated_By=''");
                ResultSet userCreation = DBQuery.getResult();
                System.out.println(userCreation);
                AlertMessages.alertMessage(userLanguage.getString("signupSuccess"));
                openHomePage(event);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void openHomePage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}

