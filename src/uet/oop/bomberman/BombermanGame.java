package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.camera.CameraTranslate;
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
import uet.oop.bomberman.menu.LevelUpScene;
import uet.oop.bomberman.menu.StatusBar;
import uet.oop.bomberman.menu.start.ViewManager;
import uet.oop.bomberman.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

public class BombermanGame extends Application {

    // fix map size
    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static int CAMERA_WIDTH = 21;
    public static int CAMERA_HEIGHT = 13;
    public static GraphicsContext gc;
    public static Canvas canvas;
    public static int score = 0;
    public static int gameStart = 0;
    public static int STATUS_BAR_HEIGHT = 32;
    public static int currentLevel = 0;
    public static int nextLevel = 1;
    public static int CAMERA_X = 0;
    public static int CAMERA_Y = 0;
    public static Group root = new Group();
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;
    public EntitySetManagement entitySetManagement = EntitySetManagement.getEntitySetManagement();

    public static void main(String[] args) {
        Sound.playSound("backGroundSound", Integer.MAX_VALUE);
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.translate(0, 0);

        stage.setResizable(false);

        root.getChildren().add(canvas);

        // tao menu
        StatusBar.createStatusBar(root);


        // Tao scene
        Scene scene = new Scene(root, CAMERA_WIDTH * Sprite.SCALED_SIZE, CAMERA_HEIGHT * Sprite.SCALED_SIZE + STATUS_BAR_HEIGHT);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("textures/head.png"));

        // khoi menu
        ViewManager viewManager = new ViewManager(scene, stage, root);


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                stage.setTitle(calculateFPSandSCORE(l));
                if (gameStart == 1) {
                    levelUp(viewManager, scene, l);
                    viewManager.startGame();
                    render();
                    update();
                    StatusBar.updateStatusBar(l);
                } else if (gameStart == 2) {
                    // render menu to play again
                    gameStart = 0;
                } else if (gameStart == 3) {
                    // win -> add img win and high score
                    // render menu play again
                    // luu high score
                    gameStart = 0;
                }
            }
        };
        timer.start();
    }

    public void update() {
        try {
            entitySetManagement.getBomberMan().update();
            entitySetManagement.getEnemyList().forEach(Entity::update);
            entitySetManagement.getGrassList().forEach(Grass::update);
            entitySetManagement.getItemList().forEach(Item::update);
            entitySetManagement.getPortal().update();
            entitySetManagement.getBrickList().forEach(Brick::update);
            entitySetManagement.getBomberMan().getBombList().forEach(Bomb::update);
            entitySetManagement.getBomberMan().getBombList().forEach(flameList -> flameList.getAllFlame().forEach(Flame::update));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void render() {
        try {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            entitySetManagement.getGrassList().forEach(grass -> grass.render(gc));
            entitySetManagement.getWallList().forEach(wall -> wall.render(gc));
            entitySetManagement.getItemList().forEach(item -> item.render(gc));
            entitySetManagement.getPortal().render(gc);
            entitySetManagement.getBrickList().forEach(brick -> brick.render(gc));
            entitySetManagement.getEnemyList().forEach(enemy -> enemy.render(gc));
            entitySetManagement.getBomberMan().getBombList().forEach(bomb -> bomb.render(gc));
            entitySetManagement.getBomberMan().getBombList().forEach(bomb -> bomb.getAllFlame().forEach(flame -> flame.render(gc)));
            entitySetManagement.getBomberMan().render(gc);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            return "Bomberman64   " + String.format(" FPS : %.2f", frameRate);
        }
        return "Bomberman64";
    }

    public void levelUp(ViewManager viewManager, Scene scene, Long l) {
        if (currentLevel < nextLevel) {
            LevelUpScene.renderScene();

            if(nextLevel < 4) {
                // render map by level
                entitySetManagement.clearAll();
                Map.createMapByLevel(nextLevel);
                currentLevel = nextLevel;

                // set camera position
                CameraTranslate.moveCamera(CAMERA_X, CAMERA_Y);
                CAMERA_X = 0;
                CAMERA_Y = 0;

                // add player's controller
                PlayerController.bomberController(scene, entitySetManagement.getBomberMan(), entitySetManagement);
            } else {
                TimerTask exit = new TimerTask() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                };
                Timer timer = new Timer();
                try {
                    timer.schedule(exit, 2000);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
