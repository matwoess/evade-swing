import view.MainView;

import javax.swing.*;

class Main {
  public static void main(String[] args) {
    setSystemLookAndFeel();
    MainView.main(args);
  }

  private static void setSystemLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException |
             InstantiationException |
             IllegalAccessException |
             UnsupportedLookAndFeelException e) {
      System.out.println("Error setting system look and feel: " + e.getMessage());
    }
  }
}