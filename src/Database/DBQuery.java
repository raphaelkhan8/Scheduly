package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuery {

    // container for db query return
    private static ResultSet result;

    // Setter (sets above result variable to query's return)
    public static void makeQuery(String query) {
        try {
            PreparedStatement ps = DBConnection.startConnection().prepareStatement(query);
            // if SELECT query, run executeQuery
            if (query.toLowerCase().startsWith("select")) {
                result = ps.executeQuery(query);
            // else (if db manipulation query), run executeUpdate
            } else {
                ps.executeUpdate(query);
            }
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
