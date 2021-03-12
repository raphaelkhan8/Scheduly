package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /** App start method: open Login Page view
     *
     * @param primaryStage - Sets the login page and title as the stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Scheduly");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /** App entry point
     *
     */
    public static void main(String[] args) {
        launch(args);
        DBConnection.stopConnection();
    }
}
