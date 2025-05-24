import db.DBConnection;
import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to connect.");
        }
    }
}
