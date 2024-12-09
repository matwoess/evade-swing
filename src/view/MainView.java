package view;

import game.GameField;
import game.Constants;
import util.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainView extends JFrame {
  private GameField gameField;
  private Timer gameTimer;
  private Timer playedTimer;
  private Timer fpsTimer;
  private float gameSpeed = 1;
  private int direction = 0;
  private float count = 0;
  private float timePlayed = -Constants.COUNTDOWN_TIME;
  private JLabel lPlayerName;
  private JLabel lHighScoreName;
  private JLabel lHighScoreTime;
  private JLabel lTime;
  private JLabel fpsLabel;

  public MainView() {
    setTitle("Evade Game");
    setSize(1000, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    String playerName = promptUserForPlayerName();

    initializeComponents(playerName);

    startTimers();

    setFocusable(true);
    setVisible(true);
    gameField.addPlayer();
  }

  private String promptUserForPlayerName() {
    String playerName = new WelcomeView(this).getPlayerName();
    if (playerName == null || playerName.isEmpty()) {
      System.exit(0);
    }
    HighScoreManager.writeAttribute(Constants.LAST_PLAYER_NAME, playerName);
    return playerName;
  }

  private void initializeComponents(String playerName) {
    lPlayerName = new JLabel(playerName);
    lHighScoreName = new JLabel();
    lHighScoreTime = new JLabel();
    lTime = new JLabel("0.0");
    fpsLabel = new JLabel("0");

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(4, 1));

    Box box = Box.createVerticalBox();
    JLabel playerTitle = new JLabel("Player Name:");
    playerTitle.setFont(playerTitle.getFont().deriveFont(Font.BOLD));
    box.add(playerTitle);
    box.add(lPlayerName);
    infoPanel.add(box);

    box = Box.createVerticalBox();
    JLabel highScoreTimeTitle = new JLabel("Time:");
    highScoreTimeTitle.setFont(highScoreTimeTitle.getFont().deriveFont(Font.BOLD));
    box.add(highScoreTimeTitle);
    box.add(lTime);
    infoPanel.add(box);

    box = Box.createVerticalBox();
    JLabel highScoreNameTitle = new JLabel("Highscore:");
    highScoreNameTitle.setFont(highScoreNameTitle.getFont().deriveFont(Font.BOLD));
    box.add(highScoreNameTitle);
    box.add(lHighScoreName);
    box.add(lHighScoreTime);
    infoPanel.add(box);

    box = Box.createVerticalBox();
    JLabel fpsLabelTitle = new JLabel("FPS:");
    fpsLabelTitle.setFont(fpsLabelTitle.getFont().deriveFont(Font.BOLD));
    box.add(fpsLabelTitle);
    box.add(fpsLabel);
    infoPanel.add(box);

    infoPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(Color.GRAY), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    add(infoPanel, BorderLayout.EAST);

    gameField = new GameField();
    add(gameField, BorderLayout.CENTER);

    // Get highscore
    String name = HighScoreManager.readAttribute(Constants.HIGH_SCORE_NAME);
    float time = Float.parseFloat(HighScoreManager.readAttribute(Constants.HIGH_SCORE_TIME));
    lHighScoreName.setText(name);
    lHighScoreTime.setText(time + " seconds");
  }

  private void startTimers() {
    gameTimer = new Timer(2, e -> gameTimerTick());
    gameTimer.start();

    playedTimer = new Timer(100, e -> playedTimerTick());
    playedTimer.start();

    fpsTimer = new Timer(500, e -> fpsTimerTick());
    fpsTimer.start();

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

  private void fpsTimerTick() {
    fpsLabel.setText(String.valueOf(gameField.getFpsCounter() * 2));
  }

  private void gameOver() {
    gameTimer.stop();
    playedTimer.stop();
    fpsTimer.stop();
    checkHighScore();
    endGame();
  }

  private void checkHighScore() {
    // Get previous high score
    float score = Float.parseFloat(lHighScoreTime.getText().split(" ")[0]);
    if (timePlayed >= score) { // high score beaten - replace it
      String newName = lPlayerName.getText();
      String newTime = lTime.getText();
      HighScoreManager.writeAttribute(Constants.HIGH_SCORE_NAME, newName);
      HighScoreManager.writeAttribute(Constants.HIGH_SCORE_TIME, newTime);
      lHighScoreName.setText(newName);
      lHighScoreTime.setText(newTime);
    }
  }

  private void endGame() {
    GameOverView gameOverDialog = new GameOverView(this, lPlayerName.getText(), lTime.getText(),
        lHighScoreName.getText(), lHighScoreTime.getText().split(" ")[0]);
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
    fpsTimer.start();
  }
}