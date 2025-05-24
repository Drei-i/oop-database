package view;

import java.awt.*;
import javax.swing.*;

public class AddHabit extends JFrame {
  public AddHabit() {
    setTitle("Add Habit");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());
    setVisible(true);
  }

  public AddHabit(int userId, Dashboard dashboard) {
    this();
    // You can use userId and dashboard here if needed
  }
}
