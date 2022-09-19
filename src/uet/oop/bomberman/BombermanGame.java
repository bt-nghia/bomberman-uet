package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.controller.PlayerController;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    // fix map size
    public static int WIDTH = 31;
    public static int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Sound.playSound("StarWars60");
        Application.launch(BombermanGame.class);
        Sound.stopSound("StarWars60");
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                try {
                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

        Map.createMapByLevel(1);
        Bomber bomberman = new Bomber(5, 5, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        Brick brick = new Brick(4, 4, Sprite.brick.getFxImage());
        EntitySetManagement.brickList.add(brick);

        PlayerController.bomberController(scene, bomberman);
    }

    public void update() throws Exception {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        EntitySetManagement.enemyList.forEach(enemy -> enemy.render(gc));
        EntitySetManagement.grassList.forEach(grass -> grass.render(gc));
        EntitySetManagement.wallList.forEach(wall -> wall.render(gc));
        entities.forEach(g -> g.render(gc));
        EntitySetManagement.brickList.forEach(brick -> brick.render(gc));
        Bomber.bombList.forEach(bomb -> bomb.render(gc));
    }
}
