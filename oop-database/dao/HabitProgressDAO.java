import java.sql.*;
import java.util.*;
import java.util.Date;
import db.DBConnection;


public class HabitProgressDAO {
    public void markHabitProgress(int habitId, Date date, boolean completed) {
        String sql = "INSERT INTO habit_progress (habit_id, date, completed) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE completed = VALUES(completed)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setBoolean(3, completed);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HabitProgress> getProgressForHabit(int habitId) {
        List<HabitProgress> progressList = new ArrayList<>();
        String sql = "SELECT * FROM habit_progress WHERE habit_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HabitProgress progress = new HabitProgress();
                progress.setProgressId(rs.getInt("progress_id"));
                progress.setHabitId(rs.getInt("habit_id"));
                progress.setDate(rs.getDate("date"));
                progress.setCompleted(rs.getBoolean("completed"));
                progressList.add(progress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progressList;
    }
}