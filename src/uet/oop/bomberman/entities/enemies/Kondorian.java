package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Kondorian extends Enemy {
    private int keepMoving = 1;
    private int lastMove = 1;

    public Kondorian(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        super.update();
        if (keepMoving == Sprite.SCALED_SIZE) {
            lastMove = randomDirec();
            keepMoving = 0;
        }
        keepMoving++;
        switch (lastMove) {
            case UP:
                goUp();
                break;
            case DOWN:
                goDown();
                break;
            case RIGHT:
                goRight();
                break;
            default:
                goLeft();
                break;
        }
        if (!this.isAlive()) {
            setImg(Sprite.kondoria_dead.getFxImage());
        }
    }

    public int randomDirec() {
        Random rand = new Random();
        int direction = rand.nextInt(4);
        return direction;
    }

    @Override
    public void goUp() {
//        System.out.println("u");
        for (int i = 0; i < 1; i++) {
            this.y--;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y++;
                break;
            }
        }
        setImg(
                Sprite.movingSprite(
                        Sprite.kondoria_right1,
                        Sprite.kondoria_right2,
                        Sprite.kondoria_right3,
                        keepMoving,
                        60
                ).getFxImage());
    }

    @Override
    public void goRight() {
//        System.out.println("r");
        for (int i = 0; i < 1; i++) {
            this.x++;
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                this.x--;
                break;
            }
        }
        setImg(
                Sprite.movingSprite(
                        Sprite.kondoria_right1,
                        Sprite.kondoria_right2,
                        Sprite.kondoria_right3,
                        keepMoving,
                        60
                ).getFxImage());
    }

    @Override
    public void goLeft() {
//        System.out.println("l");
        for (int i = 0; i < 1; i++) {
            this.x--;
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall()) {
                this.x++;
                break;
            }
        }
        setImg(
                Sprite.movingSprite(
                        Sprite.kondoria_left1,
                        Sprite.kondoria_left2,
                        Sprite.kondoria_left3,
                        keepMoving,
                        60
                ).getFxImage());
    }

    @Override
    public void goDown() {
//        System.out.println("d");
        for (int i = 0; i < 1; i++) {
            this.y++;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y--;
                break;
            }
        }
        setImg(
                Sprite.movingSprite(
                        Sprite.kondoria_left1,
                        Sprite.kondoria_left2,
                        Sprite.kondoria_left3,
                        keepMoving,
                        60
                ).getFxImage());
    }

}