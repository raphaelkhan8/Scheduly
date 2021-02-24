package Main;

import Database.DBConnection;
import Database.DBQuery;
import Model.Country;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        primaryStage.setTitle("Scheduly");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        DBConnection.stopConnection();
    }
}
