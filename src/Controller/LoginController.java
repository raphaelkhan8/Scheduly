package Controller;

import Database.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;

public class LoginController {

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
    private Label passwordLabel1;

    @FXML
    private TextField passwordTextField1;

    @FXML
    private TextArea welcomeMessageLabel;

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
