package view;

import game.Constants;
import util.HighScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WelcomeView extends JDialog {
  private JTextField txtName;

  public WelcomeView(Frame owner) {
    super(owner, "Welcome", true);
    initializeComponents();
  }

  private void initializeComponents() {
    setLayout(new BorderLayout());

    JLabel lblWelcome = new JLabel("Welcome to Evade!", SwingConstants.CENTER);
    lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
    add(lblWelcome, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel(new GridLayout(4, 1));
    JLabel lblInstruction = new JLabel("Please enter your name and press <RETURN>", SwingConstants.CENTER);
    lblInstruction.setFont(new Font("Arial", Font.PLAIN, 20));
    centerPanel.add(lblInstruction);

    txtName = new JTextField();
    txtName.setFont(new Font("Arial", Font.PLAIN, 24));
    centerPanel.add(txtName);
    txtName.setText(HighScoreManager.readAttribute(Constants.LAST_PLAYER_NAME));

    JLabel lblObjective = new JLabel("Try to avoid the falling blocks with the (yellow) player as long as possible!", SwingConstants.CENTER);
    lblObjective.setFont(new Font("Arial", Font.PLAIN, 20));
    centerPanel.add(lblObjective);

    JLabel lblControls = new JLabel("Use <LEFT> and <RIGHT> to move.", SwingConstants.CENTER);
    lblControls.setFont(new Font("Arial", Font.PLAIN, 20));
    centerPanel.add(lblControls);

    JLabel lblSpeed = new JLabel("The game will get faster every 10 seconds...", SwingConstants.CENTER);
    lblSpeed.setFont(new Font("Arial", Font.PLAIN, 20));
    centerPanel.add(lblSpeed);

    add(centerPanel, BorderLayout.CENTER);

    txtName.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          WelcomeView.this.dispose();
        }
      }
    });

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public String getPlayerName() {
    return txtName.getText();
  }
}