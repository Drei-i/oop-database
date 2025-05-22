package model;
import java.sql.Date;

public class HabitProgress {
    private int progressId;
    private int habitId;
    private Date date;
    private boolean completed;

    // Default constructor
    public HabitProgress() {}

    // Parameterized constructor
    public HabitProgress(int progressId, int habitId, Date date, boolean completed) {
        this.progressId = progressId;
        this.habitId = habitId;
        this.date = date;
        this.completed = completed;
    }

    // Getters and Setters
    public int getProgressId() {
        return progressId;
    }

    public void setProgressId(int progressId) {
        this.progressId = progressId;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}