package view;

import dao.HabitDAO;
import dao.HabitCompletionDAO;
import dao.StreakDAO;
import model.Habit;
import model.Streak;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Dashboard extends JFrame {

    private int userId; // current logged-in user id
    private JPanel habitsPanel;
    private JPanel streaksPanel;
    private JPanel graphPanel;
    private JButton addHabitButton;  // new button for adding habit

    public void deleteHabit(int habitId) {
        try {
            HabitDAO habitDAO = new HabitDAO();
            habitDAO.deleteHabit(habitId);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete habit.");
        }
    }    

    public Dashboard(int userId) {
        this.userId = userId;

        setTitle("Trackify - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout for whole frame
        setLayout(new BorderLayout());

        // ======= TOP PANEL =======
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo at top center
        ImageIcon logoIcon = new ImageIcon("src/view/assets/Logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(logoLabel);

        // "Good day!" text
        JLabel greetingLabel = new JLabel("Good day!");
        greetingLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        greetingLabel.setForeground(new Color(50, 50, 50));
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(greetingLabel);

        // Subtext
        JLabel subtextLabel = new JLabel("Let's make today count");
        subtextLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtextLabel.setForeground(new Color(80, 80, 80));
        subtextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(subtextLabel);

        add(topPanel, BorderLayout.NORTH);

        // ======= LEFT PANEL: Habits checklist with header and button =======

        // Panel for header: title and button
        JPanel habitsHeaderPanel = new JPanel(new BorderLayout());
        habitsHeaderPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel habitsTitleLabel = new JLabel("Habits to Track");
        habitsTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        habitsHeaderPanel.add(habitsTitleLabel, BorderLayout.WEST);

        addHabitButton = new JButton("Add Habit");
        habitsHeaderPanel.add(addHabitButton, BorderLayout.EAST);

        // Action for button to open AddHabit window
        addHabitButton.addActionListener(e -> {
            AddHabit addHabitWindow = new AddHabit(this);
            addHabitWindow.setVisible(true);
        });   

        // Panel for habits list with vertical BoxLayout
        habitsPanel = new JPanel();
        habitsPanel.setLayout(new BoxLayout(habitsPanel, BoxLayout.Y_AXIS));
        loadHabits();

        // Scroll pane for habits list
        JScrollPane habitsScrollPane = new JScrollPane(habitsPanel);
        habitsScrollPane.setPreferredSize(new Dimension(350, 560)); // height less than frame for header space

        // Container panel for header + scroll pane (habits list)
        JPanel habitsContainerPanel = new JPanel(new BorderLayout());
        habitsContainerPanel.setBorder(BorderFactory.createTitledBorder(""));
        habitsContainerPanel.add(habitsHeaderPanel, BorderLayout.NORTH);
        habitsContainerPanel.add(habitsScrollPane, BorderLayout.CENTER);
        habitsContainerPanel.setPreferredSize(new Dimension(500, 600));

        // ======= RIGHT PANEL =======
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, 0));
        rightPanel.setMinimumSize(new Dimension(500, 0));
        rightPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));

        // Top right: Streaks
        streaksPanel = new JPanel();
        streaksPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        streaksPanel.setBorder(BorderFactory.createTitledBorder("Streaks"));
        streaksPanel.setPreferredSize(new Dimension(500, 120));
        loadStreaks();

        rightPanel.add(streaksPanel, BorderLayout.NORTH);

        // Below streaks: Graph
        graphPanel = createCompletionGraphPanel();
        graphPanel.setBorder(BorderFactory.createTitledBorder("Habits Done Per Day"));
        rightPanel.add(graphPanel, BorderLayout.CENTER);

        // ======= MAIN PANEL: Side by side =======
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(habitsContainerPanel); // add container with header and scroll pane
        mainPanel.add(rightPanel);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Load habits from DB and add to habitsPanel with checkboxes
    public void loadHabits() {
        habitsPanel.removeAll();
        HabitDAO habitDAO = new HabitDAO();
        try {
            List<Habit> habits = habitDAO.getAllHabits();
            for (Habit habit : habits) {
                // Habit row panel with BoxLayout X_AXIS
                JPanel habitRowPanel = new JPanel();
                habitRowPanel.setLayout(new BoxLayout(habitRowPanel, BoxLayout.X_AXIS));
                habitRowPanel.setMaximumSize(new Dimension(350, 35));
                habitRowPanel.setPreferredSize(new Dimension(350, 35));
                habitRowPanel.setMinimumSize(new Dimension(350, 35));

                JCheckBox cb = new JCheckBox(habit.getName());
                cb.setPreferredSize(new Dimension(250, 25));
                cb.setMaximumSize(new Dimension(250, 25));
                cb.setMinimumSize(new Dimension(250, 25));

                JButton deleteButton = new JButton("Delete");
                deleteButton.setPreferredSize(new Dimension(80, 25));
                deleteButton.setMaximumSize(new Dimension(80, 25));
                deleteButton.setMinimumSize(new Dimension(80, 25));
                deleteButton.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "Delete this habit?", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteHabit(habit.getHabitId());
                        loadHabits();
                    }
                });

                habitRowPanel.add(cb);
                habitRowPanel.add(Box.createHorizontalGlue()); // pushes delete button to right
                habitRowPanel.add(deleteButton);

                habitsPanel.add(habitRowPanel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        habitsPanel.revalidate();
        habitsPanel.repaint();
    }    

    // Load streaks from DB and display current and longest streaks side by side
    private void loadStreaks() {
        streaksPanel.removeAll();
        StreakDAO streakDAO = new StreakDAO();
        Streak streak = streakDAO.getStreak(userId);

        int current = (streak != null) ? streak.getCurrentStreak() : 0;
        int longest = (streak != null) ? streak.getLongestStreak() : 0;

        JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));
        JLabel currentLabel = new JLabel("Current Streak");
        currentLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel currentValue = new JLabel(String.valueOf(current));
        currentValue.setFont(new Font("SansSerif", Font.PLAIN, 24));
        currentPanel.add(currentLabel);
        currentPanel.add(currentValue);

        JPanel longestPanel = new JPanel();
        longestPanel.setLayout(new BoxLayout(longestPanel, BoxLayout.Y_AXIS));
        JLabel longestLabel = new JLabel("Longest Streak");
        longestLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel longestValue = new JLabel(String.valueOf(longest));
        longestValue.setFont(new Font("SansSerif", Font.PLAIN, 24));
        longestPanel.add(longestLabel);
        longestPanel.add(longestValue);

        streaksPanel.add(currentPanel);
        streaksPanel.add(longestPanel);

        streaksPanel.revalidate();
        streaksPanel.repaint();
    }

    // Create bar chart panel using JFreeChart for habits done per day
    private JPanel createCompletionGraphPanel() {
        HabitCompletionDAO completionDAO = new HabitCompletionDAO();
        Map<String, Integer> data = completionDAO.getCompletionsPerDayOfWeek(userId);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String seriesName = "Habits Done";

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            dataset.addValue(data.getOrDefault(day, 0), seriesName, day);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Habits Done Per Day",
                "Day of Week",
                "Number of Habits Done",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return new ChartPanel(barChart);
    }

    public static void main(String[] args) {
        // for testing, pass a sample userId, e.g. 1
        SwingUtilities.invokeLater(() -> new Dashboard(1));
    }
}
