package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to database
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String userName = "";
    private static String password = "";
    public static Connection conn = null;

    /**
     * Opens the connection to the database
     */
    public static void openConnection()
    {
        try {
            conn = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection to the database
     * @return the connection to the database
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
