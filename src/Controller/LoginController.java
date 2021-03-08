package Controller;

import Database.DBQuery;
import Model.Appointment;
import Model.SessionHandler;
import Utils.AlertMessages;
import Utils.DataRetriever;
import Utils.SignInLogger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
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

    /** var to hold the logged-in username */
    private static String currentUser;
    /** container to hold user's language */
    ResourceBundle userLanguage;

    /** Initialization Override: Change text to match user's language
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SessionHandler.setLocation();
        userLanguage = SessionHandler.getUserLanguage();

        sloganLabel.setText(userLanguage.getString("sloganLabel"));
        usernameLabel.setText(userLanguage.getString("username"));
        passwordLabel.setText(userLanguage.getString("password"));
        loginMessage.setText(userLanguage.getString("loginMessage"));
        loginButton.setText(userLanguage.getString("loginButton"));
        zoneIdLabel.setText(userLanguage.getString("Country"));
        zoneIdTextField.setText(userLanguage.getString("zoneId"));
        signUpViewButton.setText(userLanguage.getString("signupViewButton"));
        signupViewLabel.setText(userLanguage.getString("signupViewMessage"));
    }

    /** Checks user input against database
     *
     * @param event - the event that triggers this function call (click Login button)
     * @throws IOException
     */
    @FXML
    void handleLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertMessages.errorMessage(userLanguage.getString("missingCredential"));
            return;
        }

        int userId = checkLoginInfo(username, password);

        if (userId > -1) {
            ObservableList<String> upcomingAptInfo = Appointment.getUpcomingAppointment(userId);
            if (upcomingAptInfo.size() == 0) {
                AlertMessages.alertMessage(userLanguage.getString("loginSuccess") + "\n\n" + userLanguage.getString("noUpcomingApt"));

            } else {
                String aptId = upcomingAptInfo.get(0);
                String aptTitle = upcomingAptInfo.get(1);
                String aptType = upcomingAptInfo.get(2);
                String localTime = DataRetriever.convertUTCTimeToLocal(upcomingAptInfo.get(3));
                String aptDate = localTime.substring(0, 10);
                String aptTime = localTime.substring(11);
                AlertMessages.warningMessage(userLanguage.getString("loginSuccess") + "\n\n"
                        + String.format(userLanguage.getString("upcomingAptMsg"), aptId, aptTitle, aptType, aptDate, aptTime));
            }
            currentUser = username;
            SignInLogger.trackLog(currentUser, true);
            openHomePage(event);
        }
        else {
            AlertMessages.errorMessage(userLanguage.getString("loginMismatch"));
            SignInLogger.trackLog(username, false);
        }
    }

    /** Checks user input against database
     *
     * @param username - the String input by the user in the username field
     * @param password - the String input by the user in the password field
     * @return - the int corresponding to the logged-in user's Id. If info does not match, -1 is returned.
     */
    public static int checkLoginInfo(String username, String password) {
        try {
            DBQuery.makeQuery("SELECT * FROM users");
            ResultSet rs = DBQuery.getResult();

            while(rs.next()) {
                if(rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password))
                    return rs.getInt("User_ID");
            }
            return -1;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /** clears currentUser variable when logging out */
    public static void clearCurrentUser() {
        currentUser = null;
    }

    /** Retrieves the logged-in user's Id
     *
     * @return - the int corresponding to the logged-in userId
     */
    public static int getCurrentUser() throws SQLException {
        int userId = 0;
        DBQuery.makeQuery("SELECT User_ID FROM users WHERE User_Name = '" + currentUser + "'");
        ResultSet rs = DBQuery.getResult();
        if (rs.next()) {
            userId = rs.getInt(1);
        }
        return userId;
    }

    /** Changes view to Sign Up page
     *
     * @param event - the event that triggers this function call (click Sign Up button)
     * @throws IOException
     */
    @FXML
    void openSignupView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/Signup.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    /** Changes view to Home Page
     *
     * @param event - the event that triggers this function call (successful login)
     * @throws IOException
     */
    @FXML
    void openHomePage(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/HomePage.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

}
