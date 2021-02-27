package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Contains properties and methods used to connect to the database */
public class DBConnection {

    private static final String protocol = DBInfo.getProtocol();
    private static final String vendorName = DBInfo.getVendorName();
    private static final String ipAddress = DBInfo.getIpAddress();
    private static final String dbName = DBInfo.getDbName();

    private static final String jdbcUrl = protocol + vendorName + ipAddress + dbName;
    private static final String MySQLJBCDriver = "com.mysql.cj.jdbc.Driver";

    private static final String username = DBInfo.getUsername();
    private static final String password = DBInfo.getPassword();
    private static Connection conn = null;

    /** Starts connection to database */
    public static Connection startConnection() {
        try {
            Class.forName(MySQLJBCDriver);
            conn = DriverManager.getConnection(jdbcUrl, username, password);
//            System.out.println("Connection successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /** Stops database connection */
    public static void stopConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
