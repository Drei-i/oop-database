import dao.HabitDAO;
import db.DBConnection;
import java.sql.Connection;

public class Main {
  public static void main(String[] args) throws Exception {
    // Connect to DB and initialize DAO
    Connection conn = DBConnection.getConnection();
    HabitDAO habitDAO = new HabitDAO();

    // Launch WelcomePage GUI on the Swing thread
    javax.swing.SwingUtilities.invokeLater(() -> {
      new view.WelcomePage().setVisible(true);
    });
  }
}
