package Controller;

import Database.DBQuery;
import Model.SessionHandler;
import Utils.AlertMessages;
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

public class LoginController implements Initializable {

    @FXML
    private TextArea loginMessage;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label zoneIdLabel;

    @FXML
    private TextField zoneIdTextField;

    @FXML
    private TextArea sloganLabel;

    @FXML
    private TextArea signupViewLabel;

    @FXML
    private Button signUpViewButton;

    // var to hold user's language
    ResourceBundle userLanguage;

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionHandler.setLocation();
        userLanguage = SessionHandler.getUserLanguage();

        sloganLabel.setText(userLanguage.getString("sloganLabel"));
        usernameLabel.setText(userLanguage.getString("username"));
        passwordLabel.setText(userLanguage.getString("password"));
        loginMessage.setText(userLanguage.getString("loginMessage"));
        loginButton.setText(userLanguage.getString("loginButton"));
        zoneIdLabel.setText(userLanguage.getString("zoneIdLabel"));
        zoneIdTextField.setText(userLanguage.getString("zoneId"));
        signUpViewButton.setText(userLanguage.getString("signupViewButton"));
        signupViewLabel.setText(userLanguage.getString("signupViewMessage"));
    }

    @FXML
    void handleLogin(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingCredential"));
            return;
        }

        boolean proceed = checkLoginInfo(username, password);

        if (proceed) {
            AlertMessages.alertMessage(userLanguage.getString("loginSuccess"));
            openHomePage(event);
        }
        else {
            AlertMessages.errorMessage(userLanguage.getString("loginMismatch"));
        }
    }

    public static Boolean checkLoginInfo(String username, String password) {
        try {
            DBQuery.makeQuery("SELECT * FROM users");
            ResultSet rs = DBQuery.getResult();

            while(rs.next()) {
                if(rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password))
                    return true;
            }
            return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @FXML
    void openSignupView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Signup.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void openHomePage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}
