package Ebamazon.model.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/Ebamazon";
    private static final String USER = "root";
    private static final String PASS = "password";

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            Connection con=DriverManager.getConnection(
                    URL,USER, PASS);
             return con;
        } catch (Exception e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
