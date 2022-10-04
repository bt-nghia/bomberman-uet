package uet.oop.bomberman.controller;

import javafx.scene.Scene;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class PlayerController {
    public static void bomberController(Scene scene, Bomber bomberman) {
        if(bomberman.isAlive()) {
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
                                bomberman.plantTheBomb();
                                break;
                        }
                    }
            );
            scene.setOnKeyReleased(event -> {
                switch (event.getCode().toString()) {
                    case "UP":
                        bomberman.setImg(Sprite.player_up.getFxImage());
                        break;
                    case "DOWN":
                        bomberman.setImg(Sprite.player_down.getFxImage());
                        break;
                    case "RIGHT":
                        bomberman.setImg(Sprite.player_right.getFxImage());
                        break;
                    case "LEFT":
                        bomberman.setImg(Sprite.player_left.getFxImage());
                        break;
                }
            });
        }
    }
}
