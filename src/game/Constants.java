package game;

import java.awt.*;
import java.nio.file.Path;

import static java.awt.Color.red;
import static java.awt.Color.yellow;

public class Constants {
  public static float GAME_SPEED_FACTOR = 0.2F; // magicnumber
  public static int BLOCK_CREATE_TICKS = 50; // magicnumber
  public static int COUNTDOWN_TIME = 2;

  public static Path HIGH_SCORE_FILE = Path.of("highscore.hs");
  public static String HIGH_SCORE_NAME = "high-score-name";
  public static String HIGH_SCORE_TIME = "high-score-time";
  public static String LAST_PLAYER_NAME = "last-player-name";

  public static int PLAYER_SIZE = 30;
  public static int PLAYER_MOVES_PEED = 1;

  public static int BLOCK_MIN_SIZE = 30;
  public static int BLOCK_MAX_SIZE = 60;
  public static int BLOCK_MIN_SPEED = 1;
  public static int BLOCK_MAX_SPEED = 2;

  public static Color PLAYER_COLOR = yellow;
  public static Color BLOCK_COLOR = red;
}