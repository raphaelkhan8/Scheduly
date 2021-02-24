package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuery {

    // container from db query return
    private static ResultSet result;

    // Setter (sets above result variable to query's return)
    public static void makeQuery(String query) {
        try {
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(query);
            result = ps.executeQuery(query);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Getter (returns result from db query)
    public static ResultSet getResult() {
        return result;
    }
}
