package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {
  public static String readAttribute(String attribute) {
    if (!game.Constants.HIGH_SCORE_FILE.toFile().exists()) {
      createTemplateFile();
    }
    try {
      List<String> fileContent = Files.readAllLines(game.Constants.HIGH_SCORE_FILE, StandardCharsets.UTF_8);
      for (String line : fileContent) {
        int assignmentIndex = line.indexOf('=');
        if (assignmentIndex == -1) {
          continue;
        }
        if (line.substring(0, assignmentIndex).equals(attribute)) {
          return line.substring(line.indexOf('=') + 1);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Error reading highscore file: " + e.getMessage());
    }
    return "";
  }

  public static void writeAttribute(String attribute, String value) {
    if (!game.Constants.HIGH_SCORE_FILE.toFile().exists()) {
      createTemplateFile();
    }
    try {
      List<String> fileContent = Files.readAllLines(game.Constants.HIGH_SCORE_FILE, StandardCharsets.UTF_8);
      List<String> newContent = new ArrayList<>();
      for (String line : fileContent) {
        int assignmentIndex = line.indexOf('=');
        if (assignmentIndex != -1 && line.substring(0, assignmentIndex).equals(attribute)) {
          line = attribute + "=" + value;
        }
        newContent.add(line);
      }
      Files.write(game.Constants.HIGH_SCORE_FILE, newContent, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Error writing highscore file: " + e.getMessage());
    }
  }

  private static void createTemplateFile() {
    try {
      String fContent = """
          [HighScore]
          high-score-name=XXX
          high-score-time=0.0
          
          [PreviousAttempt]
          last-player-name=
          """;
      Files.writeString(game.Constants.HIGH_SCORE_FILE, fContent);
    } catch (IOException e) {
      throw new RuntimeException("Error creating highscore file: " + e.getMessage());
    }
  }
}