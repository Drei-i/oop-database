package dao;
import db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseConnection;
public class HabitDAO {
    public void addHabit(Habit habit) {
        String sql = "INSERT INTO habits (user_id, name, notes) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habit.getUserId());
            stmt.setString(2, habit.getName());
            stmt.setString(3, habit.getNotes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Habit> getHabitsByUserId(int userId) {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM habits WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Habit habit = new Habit();
                habit.setHabitId(rs.getInt("habit_id"));
                habit.setUserId(rs.getInt("user_id"));
                habit.setName(rs.getString("name"));
                habit.setNotes(rs.getString("notes"));
                habits.add(habit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habits;
    }
}
