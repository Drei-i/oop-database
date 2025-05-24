package dao;

import db.DBConnection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class HabitCompletionDAO {

    // Insert a completion record when a habit is done
    public void addCompletion(int userId, int habitId, Date dateCompleted) {
        String sql = "INSERT INTO habit_completions (user_id, habit_id, date_completed) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, habitId);
            stmt.setDate(3, dateCompleted);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get count of completed habits per day of week (Monday, Tuesday, ...)
    public Map<String, Integer> getCompletionsPerDayOfWeek(int userId) {
        Map<String, Integer> result = new HashMap<>();
        // Initialize all days with 0
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            result.put(day, 0);
        }

        String sql = """
            SELECT DAYNAME(date_completed) AS day, COUNT(*) AS habit_count
            FROM habit_completions
            WHERE user_id = ?
            GROUP BY DAYNAME(date_completed)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String day = rs.getString("day");
                int count = rs.getInt("habit_count");
                result.put(day, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
