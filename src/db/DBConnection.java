package db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) return connection;

        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
