package Database;

import java.sql.ResultSet;
import java.sql.Statement;

public class DBQuery {

    // container from db query return
    private static ResultSet result;

    // Setter (sets above result variable to query's return)
    public static void makeQuery(String query) {
        try {
            Statement statement = DBConnection.startConnection().createStatement();
            result = statement.executeQuery(query);
        }
        catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    // Getter (returns result from db query)
    public static ResultSet getResult() {
        return result;
    }
}
