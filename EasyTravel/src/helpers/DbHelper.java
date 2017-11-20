package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Rustem.
 */
public class DbHelper {
    private final static String dbURL = "jdbc:postgresql://localhost:5432/EasyTravel";
    private final static String user = "postgres";
    private final static String password = "postgres";

    private static Connection connection;

    private DbHelper() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        dbURL,
                        user,
                        password
                );
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
