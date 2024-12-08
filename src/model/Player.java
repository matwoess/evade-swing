package model;

public class Player extends Entity {
  public Player() {
    this(0, 0);
  }

  public Player(int x, int y) {
    super(game.Constants.PLAYER_SIZE, game.Constants.PLAYER_COLOR, x, y);
  }
}
