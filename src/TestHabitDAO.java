import dao.HabitDAO;
import java.util.List;
import model.Habit;

public class TestHabitDAO {
    public static void main(String[] args) {
        try {
            HabitDAO habitDAO = new HabitDAO();

            // 📝 Add a habit (optional)
            Habit newHabit = new Habit();
            newHabit.setUserId(1);  // Or just hardcode any user_id value
            newHabit.setName("Test Habit");
            newHabit.setNotes("Test notes");
            habitDAO.addHabit(newHabit);
            System.out.println("✔️ Habit added!");

            // 📜 Fetch all habits
            List<Habit> habits = habitDAO.getAllHabits();
            for (Habit h : habits) {
                System.out.println("Habit ID: " + h.getHabitId());
                System.out.println("User ID: " + h.getUserId());
                System.out.println("Name: " + h.getName());
                System.out.println("Notes: " + h.getNotes());
                System.out.println("-----");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
