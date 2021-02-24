package Controller;

import Database.DBQuery;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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

    // determine user's local language
    public static Locale getLocale() {
        return Locale.getDefault();
    }

    Locale[] localeLanguages = {
            Locale.ENGLISH,
            Locale.FRENCH
    };

    // change text to match user's language upon initialization
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResourceBundle userLanguage;
        Locale current = getLocale();
        userLanguage = ResourceBundle.getBundle("Utils/Language", current);

        sloganLabel.setText(userLanguage.getString("sloganLabel"));
        usernameLabel.setText(userLanguage.getString("username"));
        passwordLabel.setText(userLanguage.getString("password"));
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        boolean proceed = checkLoginInfo(username, password);

        if (proceed) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User successfully logged in.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The username and password did not match.");
            alert.showAndWait();
        }
    }

    @FXML
    void openSignupView(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/View/signup.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
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

}
