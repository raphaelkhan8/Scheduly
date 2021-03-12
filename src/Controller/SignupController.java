package Controller;

import Database.DBQuery;
import Model.SessionHandler;
import Utils.AlertMessages;
import Utils.SignInLogger;
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

    /** Initialization Override: change text to match user's language upon initialization */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sloganLabel.setText(userLanguage.getString("sloganLabel"));
        signupMessage.setText(userLanguage.getString("signupMessage"));
        usernameLabel.setText(userLanguage.getString("username"));
        passwordLabel.setText(userLanguage.getString("password"));
        signupButton.setText(userLanguage.getString("signupButton"));
        cancelButton.setText(userLanguage.getString("cancelButton"));
    }

    /** handles Signup view user input (creates the new user in the database)
     *
      * @param event - the Event that triggers this function call (click Sign Up button)
     * @throws IOException
     */
    @FXML
    void handleSignup(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int userId = 1;

        if (username.isEmpty() || password.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingCredential"));
            return;
        }

        int loggedInUserId = LoginController.checkLoginInfo(username, password);

        if (loggedInUserId > -1) {
            AlertMessages.alertMessage(userLanguage.getString("signupRedundant"));
            return;
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
                AlertMessages.alertMessage(userLanguage.getString("signupSuccess"));
                SignInLogger.trackLog(username, true);
                openHomePage(event);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** changes view to Home Page
     *
     * @param event - the Event that triggers this function call (after successful Signup)
     * @throws IOException
     */
    @FXML
    void openHomePage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** changes view back to Login page
     *
     * @param event - the Event that triggers this function call (click Cancel button)
     * @throws IOException
     */
    @FXML
    void cancelView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}

