package uet.oop.bomberman.menu;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

public class LevelUpScene {

    private static Text level, youWin, gameOver, yourScore;
    private static Pane pane;
    private static int time = 1500;

    public static void renderScene() {
        pane = new Pane();
        pane.setStyle("-fx-background-color: BLACK");
        pane.setPrefSize(700, 500);
        int time = 1500;
        if (BombermanGame.nextLevel > 3) {
            renderYouWin();
            renderYourScore();
            pane.getChildren().add(youWin);
            pane.getChildren().add(yourScore);
            ScoreSave.saveScore();
        } else if(!EntitySetManagement.bomberMan.isAlive()) {
            renderGameOver();
            renderYourScore();
            pane.getChildren().add(gameOver);
            pane.getChildren().add(yourScore);
            time = 2000;
            ScoreSave.saveScore();
        } else {
            renderLevelUp();
            pane.getChildren().add(level);
        }

        BombermanGame.root.getChildren().add(pane);
        TimerTask lev = new TimerTask() {
            @Override
            public void run() {
                BombermanGame.gameStart = 1;
                pane.setVisible(false);
            }
        };
        Timer timer = new Timer();
        try {
            timer.schedule(lev, time);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void renderYouWin() {
        youWin = new Text("YOU WIN !");
        youWin.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        youWin.setFill(Color.WHITE);
        youWin.setX(240);
        youWin.setY(190);

    }

    public static void renderLevelUp() {
        Sound.playSound("smb", 1500);
        level = new Text("LEVEL: " + BombermanGame.nextLevel);
        level.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        level.setFill(Color.WHITE);
        level.setX(275);
        level.setY(220);
    }

    public static void renderYourScore() {
        yourScore = new Text("SCORE: " + BombermanGame.score);
        yourScore.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        yourScore.setFill(Color.WHITE);
        yourScore.setY(230);
        yourScore.setX(280);
    }

    public static void renderGameOver() {
        gameOver = new Text("GAME OVER !");
        gameOver.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        gameOver.setX(240);
        gameOver.setY(190);
        gameOver.setFill(Color.WHITE);
    }
}
