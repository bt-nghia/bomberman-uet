package uet.oop.bomberman.controller;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.entities.player.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerController {
    public static int up = -1;
    public static int right = -1;

    public static void bomberController(Scene scene, Bomber bomberman) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode().toString()) {
                case "RIGHT":
                    bomberman.goRight();
                    break;
                case "LEFT":
                    bomberman.goLeft();
                    break;
                case "UP":
                    bomberman.goUp();
                    break;
                case "DOWN":
                    bomberman.goDown();
                    break;
                case "SPACE":
                    Bomb bomb = new Bomb(bomberman.getX() / Sprite.SCALED_SIZE, bomberman.getY() / Sprite.SCALED_SIZE, Sprite.bomb_2.getFxImage())  ;
                    // check duplicate bomb
                    Map.map2D[bomb.getY()/32][bomb.getX()/32] = '6';
                    boolean duplicate = false;
                    for (Bomb bombExist : EntitySetManagement.bomberMan.bombList) {
                        if (bombExist.intersect(bomb)) {
                            duplicate = true;
                        }
                    }

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            bomb.setImg(Sprite.bomb_exploded2.getFxImage());
                            bomb.addFlameDFS();
                            bomb.setExplode(true);
                        }
                    };

                    TimerTask timerTask1 = new TimerTask() {
                        @Override
                        public void run() {
                            EntitySetManagement.brickList.removeIf(Brick::countDownEnd);
                            EntitySetManagement.bomberMan.removeBomb(bomb);
                            EntitySetManagement.removeEnemies();
                        }
                    };

                    if (!duplicate) {
                        EntitySetManagement.bomberMan.addBomb(bomb);
                        Timer timerEx = new Timer();
                        timerEx.schedule(timerTask, 2000);
                        Timer timerRev = new Timer();
                        timerRev.schedule(timerTask1, 2500L);
                    }
                    break;
            }
                }
        );
        scene.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("UP")) {
                bomberman.setImg(Sprite.player_up.getFxImage());
            } else {
                bomberman.setImg(Sprite.player_down.getFxImage());
            }
        });
    }
}
