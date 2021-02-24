package Controller;

import Database.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;

public class SignupController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button signupButton;

    @FXML
    private Label passwordLabel1;

    @FXML
    private TextField passwordTextField1;

    @FXML
    private TextArea welcomeMessageLabel;

    @FXML
    void handleSignup(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        int userId = 1;

        boolean proceed = LoginController.checkLoginInfo(username, password);

        if (proceed) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "An account with those credentials already exists. Logging in now.");
            alert.showAndWait();
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thank you for creating an account! Logging in now.");
                alert.showAndWait();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

