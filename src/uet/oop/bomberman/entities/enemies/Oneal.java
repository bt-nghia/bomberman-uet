package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.searchengine.SearchEngine;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;


public class Oneal extends Enemy {
    int slow = 0;
    int keepMoving = 0;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();
        keepMoving = keepMoving > 100 ? 0 : keepMoving + 1;
//        System.out.println("pos: " + this.y/32 + " " + this.x/32);
        int destRow = EntitySetManagement.bomberMan.getY() / Sprite.SCALED_SIZE;
        int destCol = EntitySetManagement.bomberMan.getX() / Sprite.SCALED_SIZE;

        Pair<Integer, Integer> pair = nextPosition(destRow, destCol);
        slow = slow > 100 ? 0 : slow + 1;
        if (this.y < pair.getKey() * 32) {
            if (slow % 2 == 0) {
                goDown();
            }
        }
        if (this.y > pair.getKey() * 32) {
            if (slow % 2 == 0) {
                goUp();
            }
        }
        if (this.x > pair.getValue() * 32) {
            if (slow % 2 == 0) {
                goLeft();
            }
        }
        if (this.x < pair.getValue() * 32) {
            if (slow % 2 == 0) {
                goRight();
            }
        }
    }

    @Override
    public void goUp() {
//        System.out.println("u");
        for (int i = 0; i < 2; i++) {
            this.y--;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y++;
                break;
            }
        }
        setImg(Sprite.movingSprite(
                Sprite.oneal_right1,
                Sprite.oneal_right2,
                Sprite.oneal_right3,
                keepMoving, 60
        ).getFxImage());
    }

    @Override
    public void goRight() {
//        System.out.println("r");
        for (int i = 0; i < 2; i++) {
            this.x++;
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                this.x--;
                break;
            }
        }
        setImg(Sprite.movingSprite(
                Sprite.oneal_right1,
                Sprite.oneal_right2,
                Sprite.oneal_right3,
                keepMoving, 60
        ).getFxImage());
    }

    @Override
    public void goLeft() {
//        System.out.println("l");
        for (int i = 0; i < 2; i++) {
            this.x--;
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                this.x++;
                break;
            }
        }
        setImg(Sprite.movingSprite(
                Sprite.oneal_left1,
                Sprite.oneal_left2,
                Sprite.oneal_left3,
                keepMoving, 60
        ).getFxImage());
    }

    @Override
    public void goDown() {
//        System.out.println("d");
        for (int i = 0; i < 2; i++) {
            this.y++;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y--;
                break;
            }
        }
        setImg(Sprite.movingSprite(
                Sprite.oneal_left1,
                Sprite.oneal_left2,
                Sprite.oneal_left3,
                keepMoving, 60
        ).getFxImage());
    }

    public Pair<Integer, Integer> nextPosition(int row, int col) {
        return SearchEngine.aStarSearch(
                Map.map2D,
                new Pair<>(this.y / Sprite.SCALED_SIZE, this.x / Sprite.SCALED_SIZE),
                new Pair<>(row, col)
        );
    }
}
