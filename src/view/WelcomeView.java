package view;

import game.Constants;
import util.HighScoreManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WelcomeView extends JDialog {
  private JTextField txtName;

  public WelcomeView(Frame owner) {
    super(owner, "Welcome", true);
    initializeComponents();
    setVisible(true);
  }

  private void initializeComponents() {
    JComponent contentPane = (JComponent) getContentPane();
    GridLayout layout = new GridLayout(6, 1);
    layout.setVgap(10);
    setLayout(layout);
    contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

    JLabel lblWelcome = new JLabel("Welcome to Evade!");
    lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
    lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblWelcome.setBorder(new EmptyBorder(0, 0, 20, 0));
    add(lblWelcome);

    JLabel lblInstruction = new JLabel("<html>Please enter your name and press <code>[RETURN]</code>.</html>");
    lblInstruction.setFont(new Font("Arial", Font.PLAIN, 20));
    add(lblInstruction);

    txtName = new JTextField();
    txtName.setFont(new Font("Arial", Font.PLAIN, 24));
    txtName.setText(HighScoreManager.readAttribute(Constants.LAST_PLAYER_NAME));
    add(txtName);


    JLabel lblObjective = new JLabel("Try to avoid the falling blocks with the (yellow) player as long as possible!");
    lblObjective.setFont(new Font("Arial", Font.PLAIN, 20));
    add(lblObjective);

    JLabel lblControls = new JLabel("<html>Use <code>[LEFT]</code> and <code>[RIGHT]</code> to move.</html>");
    lblControls.setFont(new Font("Arial", Font.PLAIN, 20));
    add(lblControls);

    JLabel lblSpeed = new JLabel("The game will get faster every 10 seconds...");
    lblSpeed.setFont(new Font("Arial", Font.PLAIN, 20));
    add(lblSpeed);

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
  }

  public String getPlayerName() {
    return txtName.getText();
  }
}