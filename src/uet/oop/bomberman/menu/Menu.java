package uet.oop.bomberman.menu;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Menu {
    public static Text level, bomb, time, score;
    private static ImageView startScreen;
    private static ImageView startButton;
    private static ImageView exitButton;

    public static void createMenu(Group root) {
        level = new Text("LEVEL: 1");
        level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        level.setFill(Color.WHITE);
        level.setX(100);
        level.setY(23);

        time = new Text("TIME: 0");
        time.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        time.setFill(Color.WHITE);
        time.setX(200 + 100);
        time.setY(23);

        score = new Text("SCORE: 0");
        score.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        score.setFill(Color.WHITE);
        score.setX(300 + 200);
        score.setY(23);

        startButton = new ImageView("buttons/button.png");
        startButton.setLayoutX(150);
        startButton.setLayoutY(300 + Sprite.SCALED_SIZE);
        startButton.setFitHeight(40);
        startButton.setFitWidth(160);

        exitButton = new ImageView("buttons/button.png");
        exitButton.setLayoutX(350);
        exitButton.setLayoutY(300 + Sprite.SCALED_SIZE);
        exitButton.setFitWidth(160);
        exitButton.setFitHeight(40);

        startScreen = new ImageView("textures/menuBomber.png");
        startScreen.setX(0);
        startScreen.setY(0);
        startScreen.setFitHeight(BombermanGame.CAMERA_HEIGHT * Sprite.SCALED_SIZE);
        startScreen.setFitWidth(BombermanGame.CAMERA_WIDTH * Sprite.SCALED_SIZE);
        root.getChildren().addAll(startScreen, startButton, exitButton);

        startButton.setOnMouseClicked(event -> {
            BombermanGame.gameStart = 1;
            exitMenu();
        });

        exitButton.setOnMouseClicked(event -> {
            System.exit(0);
            exitMenu();
        });

        Pane pane = new Pane();
        pane.getChildren().addAll(level, time, score);
        pane.setMinSize(BombermanGame.WIDTH * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        pane.setLayoutY(416);
        pane.setStyle("-fx-background-color: #000000");
        root.getChildren().add(pane);
    }

    public static void updateMenu(long l) {
        score.setText("SCORE: " + BombermanGame.score);
//        time.setText("TIME: " + (l - 10941714950200L));
    }

    public static void startMenu() {
        startScreen.setVisible(true);
        startButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public static void exitMenu() {
        startScreen.setVisible(false);
        startButton.setVisible(false);
        exitButton.setVisible(false);
    }
}
