package view;

import dao.HabitDAO;
import model.Habit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddHabit extends JFrame {
  private JTextField habitNameField;
  private JTextField notesField;
  private JToggleButton[] dayButtons;
  private JButton saveButton;

  public AddHabit() {
    setTitle("Add New Habit");
    setSize(400, 350);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new GridLayout(6, 1));

    // Habit Name
    add(new JLabel("Habit Name:"));
    habitNameField = new JTextField();
    add(habitNameField);

    // Notes
    add(new JLabel("Notes:"));
    notesField = new JTextField();
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
    add(saveButton);

    // Save button action
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        saveHabit();
      }
    });

    setVisible(true);
  }

  private void saveHabit() {
    try {
      String name = habitNameField.getText();
      String notes = notesField.getText();

      int userId = 1; // Replace with actual user_id if needed

      Habit habit = new Habit();
      habit.setUserId(userId);
      habit.setName(name);
      habit.setNotes(notes);

      HabitDAO habitDAO = new HabitDAO();
      int habitId = habitDAO.addHabit(habit);

      if (habitId != -1) {
        for (int i = 0; i < dayButtons.length; i++) {
          if (dayButtons[i].isSelected()) {
            habitDAO.addHabitSchedule(habitId, dayButtons[i].getText());
          }
        }

        JOptionPane.showMessageDialog(this, "Habit saved successfully!");
        dispose();
      } else {
        JOptionPane.showMessageDialog(this, "Failed to save habit.");
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error saving habit.");
    }
  }

  public static void main(String[] args) {
    new AddHabit();
  }
}
