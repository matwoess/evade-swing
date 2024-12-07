package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {
    public static String readAttribute(String attribute) {
        if (!new File(game.Constants.HIGHSCOREFILE).exists()) {
            createTemplateFile();
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(game.Constants.HIGHSCOREFILE), StandardCharsets.UTF_8))) {
            String value;
            while ((value = br.readLine()) != null) {
                if (value.isEmpty() || value.charAt(0) == '#') {
                    continue;
                }
                if (value.substring(0, value.indexOf('=')).equals(attribute)) {
                    if (value.indexOf('=') != value.length() - 1) {
                        return value.substring(value.indexOf('=') + 1);
                    } else {
                        return "";
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading highscore file: " + e.getMessage());
        }
        return "";
    }

    public static void writeAttribute(String attribute, String value) {
        if (!new File(game.Constants.HIGHSCOREFILE).exists()) {
            createTemplateFile();
        }
        try {
            List<String> fContent = new ArrayList<>(Files.readAllLines(new File(game.Constants.HIGHSCOREFILE).toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fContent.size(); i++) {
                if (fContent.get(i).isEmpty() || fContent.get(i).charAt(0) == '#') {
                    continue;
                }
                if (fContent.get(i).substring(0, fContent.get(i).indexOf('=')).equals(attribute)) {
                    fContent.set(i, attribute + "=" + value);
                }
            }
            Files.write(new File(game.Constants.HIGHSCOREFILE).toPath(), fContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error writing highscore file: " + e.getMessage());
        }
    }

    private static void createTemplateFile() {
        try {
            List<String> fContent = List.of(
                "#highscore",
                "name=XXX",
                "time=0.0"
            );
            Files.write(new File(game.Constants.HIGHSCOREFILE).toPath(), fContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error creating highscore file: " + e.getMessage());
        }
    }
}