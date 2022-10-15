package uet.oop.bomberman.menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

public class LevelUpScene {

    private static Text level;
    private static Pane pane;

    public static void renderLevelUpScene() {
        Sound.playSound("smb", 1500);
        level = new Text();
        level.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        level.setFill(Color.WHITE);
        level.setX(275);
        level.setY(220);
        level.setText("LEVEL: " + BombermanGame.nextLevel);
        pane = new Pane();
        pane.setStyle("-fx-background-color: BLACK");
        pane.setPrefSize(700, 500);
        pane.getChildren().add(level);
        BombermanGame.root.getChildren().add(pane);
        TimerTask lev = new TimerTask() {
            @Override
            public void run() {
                BombermanGame.gameStart = 1;
                pane.setVisible(false);
            }
        };
        Timer timer = new Timer();
        timer.schedule(lev, 1500);
    }
}