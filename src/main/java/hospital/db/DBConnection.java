package hospital.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class responsible for establishing a database connection.
 * <p>
 * Reads connection parameters from {@code config.properties} on the classpath
 * and provides a static factory method to obtain a {@link Connection}.
 * </p>
 */
public class DBConnection {

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;

    static {
        loadProperties();
    }

    /** Private constructor — utility class, not instantiable. */
    private DBConnection() {
    }

    /**
     * Loads database connection properties from {@code config.properties}.
     */
    private static void loadProperties() {
        Properties props = new Properties();
        try (InputStream is = DBConnection.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("config.properties not found on classpath.");
            }
            props.load(is);
            dbUrl      = props.getProperty("db.url");
            dbUsername = props.getProperty("db.username");
            dbPassword = props.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Returns a new {@link Connection} to the configured MySQL database.
     *
     * @return a live JDBC {@link Connection}
     * @throws SQLException           if a database access error occurs
     * @throws ClassNotFoundException if the MySQL driver class is not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS);
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
