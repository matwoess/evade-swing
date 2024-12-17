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
    lblWelcome.setFont(Constants.HEADER_FONT);
    lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
    lblWelcome.setBorder(new EmptyBorder(0, 0, 20, 0));
    add(lblWelcome);

    JLabel lblInstruction = new JLabel("<html>Please enter your name and press <b>[RETURN]</b>.</html>");
    lblInstruction.setFont(Constants.TEXT_FONT);
    add(lblInstruction);

    txtName = new JTextField();
    txtName.setFont(Constants.INPUT_FONT);
    txtName.setText(HighScoreManager.readAttribute(Constants.LAST_PLAYER_NAME));
    add(txtName);


    JLabel lblObjective = new JLabel("<html>Try to avoid the falling <span style=\"color: red;\">blocks</span>"+
        " with the <span style=\"color: #BA8E23;\">(yellow)</span> player as long as possible!</html>");
    lblObjective.setFont(Constants.TEXT_FONT);
    add(lblObjective);

    JLabel lblControls = new JLabel("<html>Use <b>[LEFT]</b> and <b>[RIGHT]</b> to move.</html>");
    lblControls.setFont(Constants.TEXT_FONT);
    add(lblControls);

    JLabel lblSpeed = new JLabel("<html>The game will get <i>faster</i> every 10 seconds...</html>");
    lblSpeed.setFont(Constants.TEXT_FONT);
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