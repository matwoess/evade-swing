package model;

public class Player extends Entity {
  public Player() {
    this(0, 0);
  }

  public Player(int x, int y) {
    super(game.Constants.PLAYERSIZE, game.Constants.PLAYERCOLOR, x, y);
  }
}
