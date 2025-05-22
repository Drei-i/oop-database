package dao;
import db.DBConnection;
import java.sql.*;
import java.util.*;

public class HabitScheduleDAO {
    public void setSchedule(int habitId, List<String> days) throws Exception {
        String deleteSQL = "DELETE FROM habit_schedule WHERE habit_id = ?";
        String insertSQL = "INSERT INTO habit_schedule (habit_id, day_of_week) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
            
            // Clear old schedule
            deleteStmt.setInt(1, habitId);
            deleteStmt.executeUpdate();

            // Insert new schedule
            for (String day : days) {
                insertStmt.setInt(1, habitId);
                insertStmt.setString(2, day);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSchedule(int habitId) throws Exception {
        List<String> schedule = new ArrayList<>();
        String sql = "SELECT day_of_week FROM habit_schedule WHERE habit_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                schedule.add(rs.getString("day_of_week"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }
}
