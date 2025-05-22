package model;
import java.sql.Timestamp;

public class Habit {
    private int habitId;
    private int userId;
    private String name;
    private String notes;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Habit() {}

    // Parameterized constructor
    public Habit(int habitId, int userId, String name, String notes, Timestamp createdAt, Timestamp updatedAt) {
        this.habitId = habitId;
        this.userId = userId;
        this.name = name;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}