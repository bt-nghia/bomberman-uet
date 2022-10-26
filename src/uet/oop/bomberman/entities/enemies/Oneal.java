package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import javafx.util.Pair;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.searchengine.AStar;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;


public class Oneal extends Enemy {
    private int slow = 0;
    private int keepMoving = 0;

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        super.update();
        keepMoving = keepMoving > 100 ? 0 : keepMoving + 1;
        int destRow = EntitySetManagement.getEntitySetManagement().getBomberMan().getY() / Sprite.SCALED_SIZE;
        int destCol = EntitySetManagement.getEntitySetManagement().getBomberMan().getX() / Sprite.SCALED_SIZE;
        Pair<Integer, Integer> pair = nextPosition(destRow, destCol);
//        System.out.println(destRow +  " " + destCol);
//        System.out.println(pair.toString());
//        System.out.println((this.y/32) + " " + (this.x/32));
        slow = slow > 100 ? 0 : slow + 1;
        if (pair.getKey() * Sprite.SCALED_SIZE == this.y && pair.getValue() * Sprite.SCALED_SIZE == this.x) {
            moveFree();
        } else {
            move(pair);
        }
    }

    @Override
    public void goUp() {
        for (int i = 0; i < this.getSpeed(); i++) {
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
        for (int i = 0; i < this.getSpeed(); i++) {
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
        for (int i = 0; i < this.getSpeed(); i++) {
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
        for (int i = 0; i < this.getSpeed(); i++) {
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
        return AStar.aStarSearch(
                Map.map2D,
                new Pair<>(this.y / Sprite.SCALED_SIZE, this.x / Sprite.SCALED_SIZE),
                new Pair<>(row, col)
        );
    }

    public void move(Pair<Integer, Integer> pair) {
        if (this.y < pair.getKey() * Sprite.SCALED_SIZE) {
            if (slow % 2 == 0) {
                System.out.println("d");
                goDown();
            }
        }
        if (this.y > pair.getKey() * Sprite.SCALED_SIZE) {
            if (slow % 2 == 0) {
                System.out.println("u");
                goUp();
            }
        }
        if (this.x > pair.getValue() * Sprite.SCALED_SIZE) {
            if (slow % 2 == 0) {
                System.out.println("l");
                goLeft();
            }
        }
        if (this.x < pair.getValue() * Sprite.SCALED_SIZE) {
            if (slow % 2 == 0) {
                System.out.println("r");
                goRight();
            }
        }
        if (!this.isAlive()) {
            setImg(Sprite.oneal_dead.getFxImage());
        }
    }

    public void moveFree() {
        Random random = new Random();
        int direction = random.nextInt(4);
        switch (direction) {
            case RIGHT:
                goRight();
                break;
            case LEFT:
                goLeft();
                break;
            case UP:
                goUp();
                break;
            case DOWN:
                goDown();
                break;
        }
    }
}
