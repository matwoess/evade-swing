package util;

import game.Constants;
import model.Block;
import model.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CanvasAdapter {
  private Player player;
  private List<Block> blocks = new ArrayList<>();
  private final JPanel canvas;
  private final Random rand = new Random();

  public CanvasAdapter(JPanel canvas) {
    this.canvas = canvas;
    this.canvas.setLayout(null);
  }

  public void createBlock() {
    int size = rand.nextInt(Constants.BLOCKMAXSIZE - Constants.BLOCKMINSIZE) + Constants.BLOCKMINSIZE;
    int pos = rand.nextInt(canvas.getWidth() - size);
    int speed = rand.nextInt(Constants.BLOCKMAXSPEED - Constants.BLOCKMINSPEED) + Constants.BLOCKMINSPEED;
    Block block = new Block(size, speed, pos, -size); // appear from outside canvas
    blocks.add(block);
    //canvas.add(block);
  }

  public boolean checkBlockCollisions(float gameSpeed) {
    List<Block> toRemove = new ArrayList<>();
    for (Block b : blocks) {
      // update position
      int y = b.getY();
      y += (int) (b.getMoveSpeed() * gameSpeed);
      b.getRect().setLocation(b.getX(), y);
      // check collision
      if (b.getRect().intersects(player.getRect())) {
        return true;
      }
      // remove if touches bottom
      if (y + b.getY() >= canvas.getHeight()) {
        toRemove.add(b);
      }
    }
    for (Block b : toRemove) {
      canvas.remove(b);
    }
    return false; // no collisions with player
  }

  public void addPlayer() {
    player = new Player();
    canvas.add(player);
    player.setLocation(canvas.getWidth() / 2 - Constants.PLAYERSIZE / 2,
        canvas.getHeight() - Constants.PLAYERSIZE * 5 / 3); // TODO: why?
    canvas.revalidate();
    canvas.repaint();
  }

  public void updatePlayerPosition(int direction) {
    int movement = switch (direction) {
      case -1 -> -Constants.PLAYERMOVESPEED;
      case 1 -> Constants.PLAYERMOVESPEED;
      default -> 0;
    };

    if (movement != 0) {
      int newPos = player.getX() + movement;
      newPos = Math.max(0, Math.min(newPos, canvas.getWidth() - Constants.PLAYERSIZE));
      player.setLocation(newPos, player.getY());
    }
  }

  public void reset() {
    blocks = new ArrayList<>();
    canvas.removeAll();
    canvas.add(player);
    player.setLocation(player.getX(), player.getY());
    canvas.revalidate();
    canvas.repaint();
  }
}