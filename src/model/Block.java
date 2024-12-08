package model;

public class Block extends Entity {
  public Block(int size, int speed) {
    this(size, speed, 0, 0);
  }

  public Block(int size, int speed, int x, int y) {
    super(size, game.Constants.BLOCK_COLOR, x, y);
    this.moveSpeed = speed;
  }

  private int moveSpeed;

  public int getMoveSpeed() {
    return moveSpeed;
  }

  public void setMoveSpeed(int moveSpeed) {
    this.moveSpeed = moveSpeed;
  }
}

