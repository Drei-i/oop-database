package model;
public class HabitSchedule {
    private int id;
    private int habitId;
    private String dayOfWeek; // Use "Mon", "Tue", etc.

    public HabitSchedule() {}

    public HabitSchedule(int id, int habitId, String dayOfWeek) {
        this.id = id;
        this.habitId = habitId;
        this.dayOfWeek = dayOfWeek;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getHabitId() {
        return habitId;
    }
    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
