public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        HabitDAO habitDAO = new HabitDAO();

        // Create a user
        User user = new User("Alice", "alice@example.com");
        userDAO.addUser(user);

        // Add a habit for the user
        Habit habit = new Habit();
        habit.setUserId(1); // adjust to actual user ID
        habit.setName("Morning Meditation");
        habit.setNotes("Meditate for 10 minutes");
        habitDAO.addHabit(habit);
    }
}
