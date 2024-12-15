package game;

import model.Block;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField extends JPanel {
  private Player player;
  private final List<Block> blocks = new ArrayList<>();
  private final Random rand = new Random();

  private int fpsCounter = 0;

  public GameField() {
    this.setLayout(null);
  }

  public void createBlock() {
    int size = rand.nextInt(Constants.BLOCK_MAX_SIZE + 1 - Constants.BLOCK_MIN_SIZE) + Constants.BLOCK_MIN_SIZE;
    int pos = rand.nextInt(this.getWidth() - size + 1);
    int speed = rand.nextInt(Constants.BLOCK_MAX_SPEED + 1 - Constants.BLOCK_MIN_SPEED) + Constants.BLOCK_MIN_SPEED;
    Block block = new Block(size, speed, pos, -size); // appear from outside canvas
    blocks.add(block);
  }

  public boolean checkBlockCollisions(float gameSpeed) {
    List<Block> toRemove = new ArrayList<>();
    for (Block b : blocks) {
      // update position
      int y = b.getY();
      y += (int) (b.getMoveSpeed() * gameSpeed);
      b.setY(y);
      // check collision
      if (b.getRect().intersects(player.getRect())) {
        return true;
      }
      // remove if touches bottom
      if (y + b.getSize() >= this.getHeight()) {
        toRemove.add(b);
      }
    }
    for (Block b : toRemove) {
      blocks.remove(b);
    }
    return false; // no collisions with player
  }

  public void addPlayer() {
    player = new Player();
    player.setX(this.getWidth() / 2 - Constants.PLAYER_SIZE / 2);
    player.setY(this.getHeight() - Constants.PLAYER_SIZE - 5); // 5px above ground
  }

  public void updatePlayerPosition(int direction) {
    int movement = switch (direction) {
      case -1 -> -Constants.PLAYER_MOVES_PEED;
      case 1 -> Constants.PLAYER_MOVES_PEED;
      default -> 0;
    };

    if (movement != 0) {
      int newPos = player.getX() + movement;
      newPos = Math.max(0, Math.min(newPos, this.getWidth() - Constants.PLAYER_SIZE));
      player.setX(newPos);
    }
  }

  public void reset() {
    blocks.clear();
    addPlayer();
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Constants.PLAYER_COLOR);
    g2d.fill(player.getRect());
    g2d.setColor(Color.BLACK);
    g2d.drawRect(player.getX(), player.getY(), player.getSize(), player.getSize());
    for (Block b : blocks) {
      g2d.setColor(Constants.BLOCK_COLOR);
      g2d.fill(b.getRect());
      g2d.setColor(Color.BLACK);
      g2d.drawRect(b.getX(), b.getY(), b.getSize(), b.getSize());
    }
    fpsCounter++;
  }

  public int getFpsCounter() {
    int currentFps = fpsCounter;
    fpsCounter = 0;
    return currentFps;
  }
}