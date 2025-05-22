package dao;

import java.sql.*;
import java.util.*;
import java.util.Date;
import db.DBConnection;
import model.HabitProgress;

public class HabitProgressDAO {
    public void markHabitProgress(int habitId, Date date, boolean completed) throws Exception {
        String sql = "INSERT INTO habit_progress (habit_id, date, completed) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE completed = VALUES(completed)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, habitId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setBoolean(3, completed);
            stmt.executeUpdate();
        }
    }

    public List<HabitProgress> getProgressForHabit(int habitId) throws Exception {
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
        }
        return progressList;
    }

    public void addHabitProgress(HabitProgress progress) throws Exception {
        String sql = "INSERT INTO habit_progress (habit_id, date, completed) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, progress.getHabitId());
            stmt.setDate(2, progress.getDate());
            stmt.setBoolean(3, progress.isCompleted());
            stmt.executeUpdate();
        }
    }

    public HabitProgress getHabitProgressById(int progressId) throws Exception {
        String sql = "SELECT * FROM habit_progress WHERE progress_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, progressId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HabitProgress progress = new HabitProgress();
                progress.setProgressId(rs.getInt("progress_id"));
                progress.setHabitId(rs.getInt("habit_id"));
                progress.setDate(rs.getDate("date"));
                progress.setCompleted(rs.getBoolean("completed"));
                return progress;
            }
        }
        return null;
    }

    public List<HabitProgress> getAllHabitProgress() throws Exception {
        List<HabitProgress> progressList = new ArrayList<>();
        String sql = "SELECT * FROM habit_progress";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HabitProgress progress = new HabitProgress();
                progress.setProgressId(rs.getInt("progress_id"));
                progress.setHabitId(rs.getInt("habit_id"));
                progress.setDate(rs.getDate("date"));
                progress.setCompleted(rs.getBoolean("completed"));
                progressList.add(progress);
            }
        }
        return progressList;
    }

    public void updateHabitProgress(HabitProgress progress) throws Exception {
        String sql = "UPDATE habit_progress SET habit_id = ?, date = ?, completed = ? WHERE progress_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, progress.getHabitId());
            stmt.setDate(2, progress.getDate());
            stmt.setBoolean(3, progress.isCompleted());
            stmt.setInt(4, progress.getProgressId());
            stmt.executeUpdate();
        }
    }

    public void deleteHabitProgress(int progressId) throws Exception {
        String sql = "DELETE FROM habit_progress WHERE progress_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, progressId);
            stmt.executeUpdate();
        }
    }
}