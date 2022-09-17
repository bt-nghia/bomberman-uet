package uet.oop.bomberman.controller;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.player.Bomber;

public class PlayerController {
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
    }

}
