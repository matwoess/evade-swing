package model;

import java.awt.*;

public class Entity {
  private int size;
  private int x;
  private int y;
  private final Rectangle rect;

  public Entity(int size, Color c, int x, int y) {
    this.size = size;
    this.x = x;
    this.y = y;
    this.rect = new Rectangle(size, size);
    this.rect.setLocation(x, y);
  }

  public int getSize() {
    return size;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
    this.rect.setLocation(x, this.y);
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
    this.rect.setLocation(this.x, y);
  }

  public Rectangle getRect() {
    return rect;
  }
}
