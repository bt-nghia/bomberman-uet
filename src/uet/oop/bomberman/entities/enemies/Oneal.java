package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.searchengine.SearchEngine;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;


public class Oneal extends Enemy {
    int keepMoving = 0;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
//        Pair<Integer, Integer> pair = nextPosition();
//        int tx = this.y / Sprite.SCALED_SIZE;
//        int ty = this.x / Sprite.SCALED_SIZE;
//
//
//        if (tx > pair.getValue()) {
//            moveLeft();
//        }
//        if (tx < pair.getValue()) {
//            moveRight();
//        }
//        if (ty > pair.getKey()) {
//            moveUp();
//        }
//        if (ty < pair.getKey()) {
//            moveDown();
//        }
    }

    @Override
    public void goUp() {
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        keepMoving++;
        for (int i = 0; i < 32; i++) {
            this.y--;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundBrick()) {
                this.y++;
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, keepMoving, 60).getFxImage());
    }

    @Override
    public void goRight() {
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        keepMoving++;
        for (int i = 0; i < 32; i++) {
            this.x++;
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                this.x--;
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, keepMoving, 60).getFxImage());
    }

    @Override
    public void goLeft() {
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        keepMoving++;
        this.x -= 5;
        for (int i = 0; i < 32; i++) {
            this.x--;
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                this.x++;
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, keepMoving, 60).getFxImage());
    }

    @Override
    public void goDown() {
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        keepMoving++;
        for (int i = 0; i < 32; i++) {
            this.y++;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundBrick()) {
                this.y--;
                break;
            }
        }
        setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, keepMoving, 60).getFxImage());
    }

//    public Pair<Integer, Integer> nextPosition() {
//        return SearchEngine.aStarSearch(
//                Map.createMapByLevel(1),
//                new Pair<>(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE),
//                new Pair<>(EntitySetManagement.bomberMan.getX() / Sprite.SCALED_SIZE, EntitySetManagement.bomberMan.getY() / Sprite.SCALED_SIZE)
//        );
//    }
}
