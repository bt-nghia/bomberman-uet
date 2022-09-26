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
import uet.oop.bomberman.entities.enemies.Kondorian;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Grass;
import uet.oop.bomberman.entities.player.bomb.Bomb;
import uet.oop.bomberman.entities.player.bomb.Flame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    // fix map size
    public static int WIDTH = 29;
    public static int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;


    public static void main(String[] args) {
//        Sound.playSound("StarWars60");
        Application.launch(BombermanGame.class);
//        Sound.stopSound("StarWars60");
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
        Map.createMapByLevel(2);
        PlayerController.bomberController(scene, EntitySetManagement.bomberMan);
        EntitySetManagement.enemyList.add(new Oneal(5, 7, Sprite.oneal_right1.getFxImage()));
        EntitySetManagement.enemyList.add(new Kondorian(6,7,Sprite.kondoria_right1.getFxImage()));
    }

    public void update() throws Exception {
        EntitySetManagement.bomberMan.update();
        EntitySetManagement.enemyList.forEach(Entity::update);
        EntitySetManagement.grassList.forEach(Grass::update);
        EntitySetManagement.brickList.forEach(Brick::update);
        EntitySetManagement.bomberMan.bombList.forEach(Bomb::update);
        EntitySetManagement.bomberMan.bombList.forEach(flameList -> flameList.getAllFlame().forEach(Flame::update));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        EntitySetManagement.grassList.forEach(grass -> grass.render(gc));
        EntitySetManagement.wallList.forEach(wall -> wall.render(gc));
        EntitySetManagement.brickList.forEach(brick -> brick.render(gc));
        EntitySetManagement.enemyList.forEach(enemy -> enemy.render(gc));
        EntitySetManagement.bomberMan.bombList.forEach(bomb -> bomb.render(gc));
        EntitySetManagement.bomberMan.bombList.forEach(bomb -> bomb.allFlame.forEach(flame -> flame.render(gc)));
        EntitySetManagement.bomberMan.render(gc);
    }
}
