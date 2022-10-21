package uet.oop.bomberman.menu;

import uet.oop.bomberman.BombermanGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ScoreSave {
    public static void saveScore() {
        try {
            Files.write(
                    Paths.get("res/score/highscores.txt"),
                    ("\n" + ((Integer) BombermanGame.score).toString()).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
