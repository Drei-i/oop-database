package view;

import dao.HabitDAO;
import db.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;
import model.Habit;

public class AddHabit extends JFrame {

  private static final Color BG_COLOR = new Color(237, 230, 214);
  private static final Color BUTTON_COLOR = new Color(168, 152, 136);
  private static final Color CARD_COLOR = new Color(255, 247, 237);
  private static final Color TEXT_COLOR = new Color(50, 50, 50);

  private JTextField habitNameField;
  private JTextField notesField;
  private JToggleButton[] dayButtons;
  private JButton saveButton;
  private Dashboard dashboard;

  public AddHabit(Dashboard dashboard) {
    this.dashboard = dashboard;

    setTitle("Add New Habit");
    setSize(700, 550);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridLayout(6, 1));

    getContentPane().setBackground(new Color(237, 230, 214));

    JLabel habitNameLabel = new JLabel("Habit Name:");
    habitNameLabel.setFont(new Font("Georgia", Font.BOLD, 16));
    add(habitNameLabel);
    habitNameField = new JTextField();
    habitNameField.setFont(new Font("Georgia", Font.PLAIN, 14));
    add(habitNameField);

    JLabel notesLabel = new JLabel("Notes:");
    notesLabel.setFont(new Font("Georgia", Font.BOLD, 16));
    add(notesLabel);
    notesField = new JTextField();
    notesField.setFont(new Font("Georgia", Font.PLAIN, 14));
    add(notesField);

    // Days of the week toggle buttons
    JPanel daysPanel = new JPanel();
    daysPanel.setLayout(new GridLayout(1, 7));

    String[] days = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    dayButtons = new JToggleButton[7];
    for (int i = 0; i < days.length; i++) {
      dayButtons[i] = new JToggleButton(days[i]);
      daysPanel.add(dayButtons[i]);
    }
    add(daysPanel);

    // Save button
    saveButton = new JButton("Save Habit");
    saveButton.setBackground(new Color(168, 152, 136));
    saveButton.setFont(new Font("Georgia", Font.BOLD, 14));
    add(saveButton);

    // Save button action
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        saveHabit();
      }
    });

    dashboard.loadHabits();

    setVisible(true);
  }

  private void saveHabit() {
    Connection conn = null;
    try {
      String name = habitNameField.getText().trim();
      String notes = notesField.getText().trim();
      int userId = 1; // Replace this with actual user_id if needed

      if (name.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter a habit name.");
        return;
      }

      Habit habit = new Habit();
      habit.setUserId(userId);
      habit.setName(name);
      habit.setNotes(notes);

      HabitDAO habitDAO = new HabitDAO();
      conn = DBConnection.getConnection();

      if (conn == null) {
        JOptionPane.showMessageDialog(this, "Failed to connect to database.");
        return;
      }

      conn.setAutoCommit(false); // Start transaction

      int habitId = habitDAO.addHabit(conn, habit);

      if (habitId != -1) {
        for (JToggleButton dayButton : dayButtons) {
          if (dayButton.isSelected()) {
            habitDAO.addHabitSchedule(conn, habitId, dayButton.getText());
          }
        }

        conn.commit(); // Commit transaction
        JOptionPane.showMessageDialog(this, "Habit saved successfully!");
        dispose();
      } else {
        conn.rollback();
        JOptionPane.showMessageDialog(this, "Failed to save habit.");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      try {
        if (conn != null)
          conn.rollback();
      } catch (Exception rollbackEx) {
        rollbackEx.printStackTrace();
      }
      JOptionPane.showMessageDialog(this, "Error saving habit.");
    } finally {
      try {
        if (conn != null)
          conn.close();
      } catch (Exception closeEx) {
        closeEx.printStackTrace();
      }
    }

    // Refresh habits list on Dashboard
    dashboard.loadHabits();

    // Close add habit window
    this.dispose();

  }
}
