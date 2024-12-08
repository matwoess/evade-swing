package view;

import game.GameField;
import game.Constants;
import util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainView extends JFrame {
  private final GameField gameField;
  private final Timer gameTimer;
  private final Timer playedTimer;
  private float gameSpeed = 1;
  private int direction = 0;
  private float count = 0;
  private float timePlayed = -Constants.COUNTDOWN_TIME;
  private final JLabel lPlayerName;
  private final JLabel lHighScoreName;
  private final JLabel lHighScoreTime;
  private final JLabel lTime;
  private final JLabel fpsLabel;

  public MainView() {
    setTitle("Evade Game");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Prompt user for name
    String playerName = new WelcomeView(this).getPlayerName();
    if (playerName == null || playerName.isEmpty()) {
      System.exit(0);
    }
    HighScoreManager.writeAttribute(Constants.LAST_PLAYER_NAME, playerName);

    lPlayerName = new JLabel(playerName);
    lHighScoreName = new JLabel();
    lHighScoreTime = new JLabel();
    lTime = new JLabel("0.0");

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(1, 4));
    infoPanel.add(new JLabel("Player:"));
    infoPanel.add(lPlayerName);
    infoPanel.add(new JLabel("Highscore:"));
    infoPanel.add(lHighScoreName);
    infoPanel.add(lHighScoreTime);
    infoPanel.add(new JLabel("Time:"));
    infoPanel.add(lTime);
    fpsLabel = new JLabel("FPS: 0");
    infoPanel.add(fpsLabel);
    add(infoPanel, BorderLayout.NORTH);

    gameField = new GameField();
    add(gameField, BorderLayout.CENTER);

    // Get highscore
    String name = HighScoreManager.readAttribute(Constants.HIGH_SCORE_NAME);
    float time = Float.parseFloat(HighScoreManager.readAttribute(Constants.HIGH_SCORE_TIME));
    lHighScoreName.setText(name);
    lHighScoreTime.setText(String.valueOf(time));

    // Start timers
    gameTimer = new Timer(2, e -> gameTimerTick());
    gameTimer.start();

    playedTimer = new Timer(100, e -> playedTimerTick());
    playedTimer.start();

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        onKeyDownHandler(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        onKeyUpHandler(e);
      }
    });

    setFocusable(true);
    setVisible(true);
    gameField.addPlayer();
  }

  private void onKeyDownHandler(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      direction = -1;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      direction = 1;
    }
  }

  private void onKeyUpHandler(KeyEvent e) {
    if ((direction == -1 && e.getKeyCode() == KeyEvent.VK_LEFT) || (direction == 1 && e.getKeyCode() == KeyEvent.VK_RIGHT)) {
      direction = 0;
    }
  }

  private void playedTimerTick() {
    gameSpeed = 1 + (int) (timePlayed / 10) * Constants.GAME_SPEED_FACTOR;
    timePlayed += 0.1F;
    lTime.setText(String.format("%.1f", timePlayed));
  }

  private void gameTimerTick() {
    gameField.updatePlayerPosition(direction);
    if (timePlayed >= 0) { // countdown to game-start
      boolean gameOver = gameField.checkBlockCollisions(gameSpeed);
      if (gameOver) {
        gameOver();
      }
      count += gameSpeed;
      if (count >= Constants.BLOCK_CREATE_TICKS) {
        count = 0;
        gameField.createBlock();
      }
    }
  }

  private void gameOver() {
    gameTimer.stop();
    playedTimer.stop();
    checkHighscore();
    endGame();
  }

  private void checkHighscore() {
    // Get previous highscore
    float score = Float.parseFloat(lHighScoreTime.getText());
    if (timePlayed >= score) { // highscore beaten - replace it
      String newName = lPlayerName.getText();
      String newTime = lTime.getText();
      HighScoreManager.writeAttribute(Constants.HIGH_SCORE_NAME, newName);
      HighScoreManager.writeAttribute(Constants.HIGH_SCORE_TIME, newTime);
      lHighScoreName.setText(newName);
      lHighScoreTime.setText(newTime);
    }
  }

  private void endGame() {
    GameOverView gameOverDialog = new GameOverView(this, lPlayerName.getText(), lTime.getText(), lHighScoreName.getText(), lHighScoreTime.getText());
    if (gameOverDialog.getReplay()) {
      restartGame();
    } else {
      System.exit(0);
    }
  }

  private void restartGame() {
    // Reset game variables
    gameSpeed = 1;
    direction = 0;
    count = 0;
    timePlayed = -Constants.COUNTDOWN_TIME;
    // Reset game field
    gameField.reset();
    // Get high score
    String name = HighScoreManager.readAttribute(Constants.HIGH_SCORE_NAME);
    float time = Float.parseFloat(HighScoreManager.readAttribute(Constants.HIGH_SCORE_TIME));
    lHighScoreName.setText(name);
    lHighScoreTime.setText(String.valueOf(time));
    gameTimer.start();
    playedTimer.start();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(MainView::new);
  }
}