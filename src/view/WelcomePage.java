package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class WelcomePage extends JFrame {

    private JButton getStartedButton;
    private JLabel logoLabel, messageLabel;
    private Image backgroundImage;

    public WelcomePage() {
        // Frame settings
        setTitle("Trackify - Welcome");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load background image using resource path
        backgroundImage = new ImageIcon(getClass().getResource("/view/assets/background.jpg")).getImage();

        // Custom panel with background image and gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw background image scaled to panel size
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                // Gradient overlay
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(217, 217, 217, 100),
                    0, getHeight(), new Color(0, 0, 0, 150)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Use BoxLayout to center contents vertically
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Load logo image using resource path
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/view/assets/Logo.png"));
        logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Message label
        messageLabel = new JLabel("Build better habits, one day at a time.");
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        messageLabel.setForeground(new Color(50, 50, 50));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Get Started button
        getStartedButton = new JButton("Get Started");
        getStartedButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        getStartedButton.setBackground(new Color(168, 152, 136));
        getStartedButton.setForeground(Color.WHITE);
        getStartedButton.setFocusPainted(false);
        getStartedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStartedButton.setMaximumSize(new Dimension(200, 50)); // fixed size

        getStartedButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              // Open Dashboard window
              new Dashboard(1);
              // Close this window
              dispose();
          }
      });
      

        // Add components to panel
        backgroundPanel.add(logoLabel);
        backgroundPanel.add(messageLabel);
        backgroundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        backgroundPanel.add(getStartedButton);

        // Add panel to frame
        setContentPane(backgroundPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}
