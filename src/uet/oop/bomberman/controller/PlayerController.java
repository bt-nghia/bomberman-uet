package uet.oop.bomberman.controller;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

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
                    }
                }
        );
        scene.setOnKeyReleased(event -> {
            if(event.getCode().toString().equals("UP")) {
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
