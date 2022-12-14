package uet.oop.bomberman.menu;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class StatusBar {
    public static int countDownTime = 20000;
    public static Text level, bomb, time, score;

    public static void createStatusBar(Group root) {
        level = new Text("LEVEL: 1");
        level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        level.setFill(Color.WHITE);
        level.setX(100);
        level.setY(23);

        time = new Text("TIME: " + countDownTime);
        time.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        time.setFill(Color.WHITE);
        time.setX(200 + 100);
        time.setY(23);

        score = new Text("SCORE: 0");
        score.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        score.setFill(Color.WHITE);
        score.setX(300 + 200);
        score.setY(23);

        Pane pane = new Pane();
        pane.getChildren().addAll(level, time, score);
        pane.setMinSize(BombermanGame.WIDTH * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        pane.setLayoutY(416);
        pane.setStyle("-fx-background-color: #000000");
        root.getChildren().add(pane);
    }

    public static void updateStatusBar(long l) {
        score.setText("SCORE: " + BombermanGame.score);
        level.setText("LEVEL: " + BombermanGame.currentLevel);
        time.setText("TIME: " + countDownTime / 100);
        countDownTime -= 2;
        if (countDownTime == 0) {
            System.exit(0);
        }
    }
}
