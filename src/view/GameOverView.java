package view;

import util.HighScoreManager;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameOverView extends JDialog {
  private static final String FORMAT_HIGH_SCORE = "Excellent %s! You have beaten the highscore\nby surviving for %s seconds.";
  private static final String FORMAT_WON = "Well done, %s. You have survived for %s seconds.\nThe highscore is by %s with %s seconds.";

  public GameOverView(String playerName, String playerTime, String highscoreName, String highscoreTime) {
    setTitle("Game Over");
    JLabel lblResult = new JLabel();
    if (Float.parseFloat(playerTime) >= Float.parseFloat(highscoreTime)) {
      lblResult.setText(String.format(FORMAT_HIGH_SCORE, playerName, playerTime));
    } else {
      lblResult.setText(String.format(FORMAT_WON, playerName, playerTime, highscoreName, highscoreTime));
    }
    add(lblResult);
    pack();
    setLocationRelativeTo(null);
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        onKeyDownHandler(e);
      }
    });
    setVisible(true);
  }

  private void onKeyDownHandler(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      this.dispose();
    } else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
      resetScore();
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      this.dispose();
    }
  }

  private void resetScore() {
    HighScoreManager.writeAttribute("name", "XXX");
    HighScoreManager.writeAttribute("time", "00.0");
  }
}