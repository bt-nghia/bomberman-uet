package uet.oop.bomberman.controller;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.entities.player.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerController {
    public static int up = -1;
    public static int right = -1;

    public static void bomberController(Scene scene, Bomber bomberman) {
        scene.setOnKeyPressed(event -> {
                    if (event.getCode().toString().equals("RIGHT")) {
                        bomberman.goRight();
                    } else if (event.getCode().toString().equals("LEFT")) {
                        bomberman.goLeft();
                    } else if (event.getCode().toString().equals("UP")) {
                        bomberman.goUp();
                    } else if (event.getCode().toString().equals("DOWN")) {
                        bomberman.goDown();
                    } else if (event.getCode().toString().equals("SPACE")) {
                        Bomb bomb = new Bomb(bomberman.getX() / Sprite.SCALED_SIZE, bomberman.getY() / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage());
                        // check duplicate bomb
                        boolean duplicate = false;
                        for(Bomb bombExist : Bomber.bombList) {
                            if(bombExist.intersect(bomb)) {
                                duplicate = true;
                            }
                        }

                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                bomb.setImg(Sprite.bomb_exploded.getFxImage());
                                bomb.setVisible(true);
                                bomb.addFlameDFS();
                            }
                        };

                        TimerTask timerTask1 = new TimerTask() {
                            @Override
                            public void run() {
                                EntitySetManagement.brickList.removeIf(Brick::isBroken);
//                                EntitySetManagement.bomberman.removeBomb(bomb);
//                                EntitySetManagement.removeEnemy();
                            }
                        };

                        if (!duplicate/**/) {
                            EntitySetManagement.bomberManManagement.addBomb(bomb);
                            Timer timerEx = new Timer();
                            timerEx.schedule(timerTask, 2000);
                            Timer timerRev = new Timer();
                            timerRev.schedule(timerTask1, 3000L);
                        }
                    }
                }
        );
        scene.setOnKeyReleased(event -> {
            if (event.getCode().toString().equals("UP")) {
                bomberman.setImg(Sprite.player_up.getFxImage());
            } else {
                bomberman.setImg(Sprite.player_down.getFxImage());
            }
//            bomberman.roundPosition(up, right);
//            up = -1;
//            right = -1;
        });
    }
}
