import dao.HabitDAO;
import dao.UserDAO;
import db.DBConnection;
import java.sql.Connection;
import model.Habit;
import model.User;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = DBConnection.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        HabitDAO habitDAO = new HabitDAO();

        // CREATE
        User user = new User("Alice", 25, "alice@example.com");
        userDAO.addUser(user);
        System.out.println("User added!");

        // READ ALL
        List<User> users = userDAO.getAllUsers();
        System.out.println("All users:");
        for (User u : users) {
            System.out.println("User: " + u.getName() + ", " + u.getAge() + ", " + u.getEmail());
        }

        // READ BY ID (using the first user's id)
        if (!users.isEmpty()) {
            User fetched = userDAO.getUserById(users.get(0).getUserId());
            if (fetched != null) {
                System.out.println("Fetched user by ID: " + fetched.getName());
            }

            // UPDATE
            fetched.setName("Alice Updated");
            userDAO.updateUser(fetched, users.get(0).getUserId());
            System.out.println("User updated!");

            // DELETE
            userDAO.deleteUser(users.get(0).getUserId());
            System.out.println("User deleted!");
        }
    }
}
