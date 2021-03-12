package Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuery {

    /** container for db query return */
    private static ResultSet result;

    /** Setter (sets above result variable to query's return)
     *
     * @param query - the String query to be executed against the database
     */
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

    /** Getter
     *
     * @return - the ResultSet corresponding to the query return from above
     */
    public static ResultSet getResult() {
        return result;
    }
    
}
