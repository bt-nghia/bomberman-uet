package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uet.oop.bomberman.controller.PlayerController;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class BombermanGame extends Application {

    // fix map size
    public static int WIDTH = 31;
    public static int HEIGHT = 13;

    private static GraphicsContext gc;
    private static Canvas canvas;

    public static int score = 0;
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    public static void main(String[] args) {
        Sound.playSound("backGroundSound");
        Application.launch(BombermanGame.class);
        Sound.stopSound("backGroundSound");
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.translate(0, 0);
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root, 15 * 32, Sprite.SCALED_SIZE * HEIGHT);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(Sprite.player_right_1.getFxImage());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                stage.setTitle(calculateFPSandSCORE(l));
                render();
                update();
            }
        };
        timer.start();
        Map.createMapByLevel(1);
        PlayerController.bomberController(scene, EntitySetManagement.bomberMan);
    }

    public void update() {
        try {
            EntitySetManagement.bomberMan.update();
            EntitySetManagement.enemyList.forEach(Entity::update);
            EntitySetManagement.grassList.forEach(Grass::update);
            EntitySetManagement.itemList.forEach(Item::update);
            EntitySetManagement.brickList.forEach(Brick::update);
            EntitySetManagement.bomberMan.bombList.forEach(Bomb::update);
            EntitySetManagement.bomberMan.bombList.forEach(flameList -> flameList.getAllFlame().forEach(Flame::update));
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
    }

    public void render() {
        try {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            EntitySetManagement.grassList.forEach(grass -> grass.render(gc));
            EntitySetManagement.wallList.forEach(wall -> wall.render(gc));
            EntitySetManagement.itemList.forEach(item -> item.render(gc));
            EntitySetManagement.brickList.forEach(brick -> brick.render(gc));
            EntitySetManagement.enemyList.forEach(enemy -> enemy.render(gc));
            EntitySetManagement.bomberMan.bombList.forEach(bomb -> bomb.render(gc));
            EntitySetManagement.bomberMan.bombList.forEach(bomb -> bomb.allFlame.forEach(flame -> flame.render(gc)));
            EntitySetManagement.bomberMan.render(gc);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }
    }

    public String calculateFPSandSCORE(long now) {
        long oldFrameTime = frameTimes[frameTimeIndex];
        frameTimes[frameTimeIndex] = now;
        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
        if (frameTimeIndex == 0) {
            arrayFilled = true;
        }
        if (arrayFilled) {
            long elapsedNanos = now - oldFrameTime;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
            double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
            return "Bomberman64 |  " + String.format(" FPS : %.2f", frameRate) + "   SCORE : " + score;
        }
        return "Bomberman64";
    }

    public static void moveCamera(int x, int y) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.translate(-x, -y);
    }
}
