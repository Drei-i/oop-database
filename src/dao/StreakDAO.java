package dao;
import db.DBConnection;
import java.sql.*;
import model.Streak;

public class StreakDAO {
    public void updateStreak(int userId, int current, int longest, Date lastUpdated) {
        String sql = """
            INSERT INTO streaks (user_id, current_streak, longest_streak, last_updated)
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                current_streak = VALUES(current_streak),
                longest_streak = VALUES(longest_streak),
                last_updated = VALUES(last_updated)
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, current);
            stmt.setInt(3, longest);
            stmt.setDate(4, new java.sql.Date(lastUpdated.getTime()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Streak getStreak(int userId) {
        String sql = "SELECT * FROM streaks WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Streak streak = new Streak();
                streak.setUserId(userId);
                streak.setCurrentStreak(rs.getInt("current_streak"));
                streak.setLongestStreak(rs.getInt("longest_streak"));
                streak.setLastUpdated(rs.getDate("last_updated"));
                return streak;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
