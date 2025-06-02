package dao;
import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Habit;

public class HabitDAO {

    public int addHabit(Connection conn, Habit habit) {
        String sql = "INSERT INTO habits (user_id, name, notes) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, habit.getUserId());
            stmt.setString(2, habit.getName());
            stmt.setString(3, habit.getNotes());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }    

    // Original method using internal connection (optional)
    public int addHabit(Habit habit) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            return addHabit(conn, habit);
        }
    }

    public List<Habit> getHabitsByUserId(int userId) throws Exception {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM habits WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
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

    public Habit getHabitById(int habitId) throws Exception {
        String sql = "SELECT * FROM habits WHERE habit_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Habit habit = new Habit();
                habit.setHabitId(rs.getInt("habit_id"));
                habit.setUserId(rs.getInt("user_id"));
                habit.setName(rs.getString("name"));
                habit.setNotes(rs.getString("notes"));
                // Set createdAt and updatedAt if needed
                return habit;
            }
        }
        return null;
    }

    public List<Habit> getAllHabits() throws Exception {
        List<Habit> habits = new ArrayList<>();
        String sql = "SELECT * FROM habits";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Habit habit = new Habit();
                habit.setHabitId(rs.getInt("habit_id"));
                habit.setUserId(rs.getInt("user_id"));
                habit.setName(rs.getString("name"));
                habit.setNotes(rs.getString("notes"));
                habits.add(habit);
            }
        }
        return habits;
    }

    public void updateHabit(Habit habit) throws Exception {
        String sql = "UPDATE habits SET user_id = ?, name = ?, notes = ? WHERE habit_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habit.getUserId());
            stmt.setString(2, habit.getName());
            stmt.setString(3, habit.getNotes());
            stmt.setInt(4, habit.getHabitId());
            stmt.executeUpdate();
        }
    }

    public void deleteHabit(int habitId) throws Exception {
        String sql = "DELETE FROM habits WHERE habit_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            stmt.executeUpdate();
        }
    }

    public void addHabitSchedule(Connection conn, int habitId, String dayOfWeek) throws SQLException {
        String sql = "INSERT INTO habit_schedule (habit_id, day_of_week) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            stmt.setString(2, dayOfWeek);
            stmt.executeUpdate();
        }
    }

    // Optional overload
    public void addHabitSchedule(int habitId, String dayOfWeek) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            addHabitSchedule(conn, habitId, dayOfWeek);
        }
    }
}
