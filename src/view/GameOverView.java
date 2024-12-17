package view;

import game.Constants;
import util.HighScoreManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameOverView extends JDialog {
  private Boolean replay = false;

  public GameOverView(Frame owner, String playerName, String playerTime, String highScoreName, String highScoreTime) {
    super(owner, "Game Over", true);
    initializeComponents(playerName, playerTime, highScoreName, highScoreTime);
    setVisible(true);
  }

  private void initializeComponents(String playerName, String playerTime, String highScoreName, String highScoreTime) {
    JComponent contentPane = (JComponent) getContentPane();
    GridLayout layout = new GridLayout(5, 1);
    layout.setVgap(10);
    setLayout(layout);
    contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

    boolean beatenHighScore = Float.parseFloat(playerTime) >= Float.parseFloat(highScoreTime);

    JLabel lblGameOver = new JLabel("Game Over");
    lblGameOver.setFont(Constants.HEADER_FONT);
    lblGameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(lblGameOver);

    JLabel lblCongratulations = new JLabel();
    lblCongratulations.setFont(Constants.TEXT_FONT);
    if (beatenHighScore) {
      lblCongratulations.setText("<html>Congratulations, <b>%s</b>! You have beaten the high score.".formatted(playerName));
    } else {
      lblCongratulations.setText("Well done, %s.".formatted(playerName));
    }
    add(lblCongratulations);

    JLabel lblResult = new JLabel();
    lblResult.setFont(Constants.TEXT_FONT);
    lblResult.setText("<html>You survived for <b>%s</b> seconds.</html>".formatted(playerTime));
    add(lblResult);

    JLabel lblPrevHighScore = new JLabel();
    lblPrevHighScore.setFont(Constants.TEXT_FONT);
    if (beatenHighScore) {
      lblPrevHighScore.setText("<html>The previous high score was <b>%s</b> seconds by <b>%s</b>.</html>".formatted(highScoreTime, highScoreName));
    } else {
      lblPrevHighScore.setText("<html>The current high score is <b>%s</b> seconds by <b>%s</b>.</html>".formatted(highScoreTime, highScoreName));
    }
    add(lblPrevHighScore);

    JLabel lblInstructions = new JLabel();
    lblInstructions.setFont(Constants.TEXT_FONT);
    lblInstructions.setText("<html>Press <b>[RETURN]</b> to retry or <b>[ESCAPE]</b> to quit.</html>");
    add(lblInstructions);

    pack();
    setLocationRelativeTo(null);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        onKeyDownHandler(e);
      }
    });
  }

  private void onKeyDownHandler(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      replay = true;
      this.dispose();
    } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
      resetScore();
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      replay = false;
      this.dispose();
    }
  }

  private void resetScore() {
    HighScoreManager.writeAttribute(Constants.HIGH_SCORE_NAME, "XXX");
    HighScoreManager.writeAttribute(Constants.HIGH_SCORE_TIME, "0.0");
  }

  public Boolean getReplay() {
    return replay;
  }
}