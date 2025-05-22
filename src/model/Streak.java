package model;
import java.sql.Date;

public class Streak {
    private int userId;
    private int currentStreak;
    private int longestStreak;
    private Date lastUpdated;

    // Default constructor
    public Streak() {}

    // Parameterized constructor
    public Streak(int userId, int currentStreak, int longestStreak, Date lastUpdated) {
        this.userId = userId;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}