package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Kondorian extends Enemy{

    public Kondorian(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
//        if (isAlive()) {
//            if (this.getSpeedX() == 0) {
//                this.y += this.getSpeedY();
//                if (checkBoundWall() || checkBoundBomb() || getY() % Sprite.SCALED_SIZE == 0) {
//                    if (getY() % Sprite.SCALED_SIZE != 0) {
//                        this.y -= this.getSpeedY();
//                    }
////                    this.randomDirection();
//                }
//            } else {
//                this.x += this.getSpeedX();
//                if (checkBoundBomb() || checkBoundWall() || getX() % Sprite.SCALED_SIZE == 0) {
//                    if (getX() % Sprite.SCALED_SIZE != 0) {
//                        this.x -= this.getSpeedX();
//                    }
////                    this.randomDirection();
//                }
//            }
//        } else {
////            this.img = Sprite.kondoria_die.getFxImage();
//        }
//        if (isAlive()) {
//            if (this.getSpeedX() > 0) {
//                this.img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2
//                        , Sprite.kondoria_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
//            } else if (this.getSpeedX() < 0){
//                this.img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2
//                        , Sprite.kondoria_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
//            } else if (this.getSpeedY() > 0) {
//                this.img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2
//                        , Sprite.kondoria_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
//            } else {
//                this.img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2
//                        , Sprite.kondoria_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
//            }
//        } else {
////            this.img = Sprite.kondoria_die.getFxImage();
//        }
        int randomDirection = randomMove();
        switch (randomDirection) {
            case 1:
                goDown();
                break;
            case 2:
                goLeft();
                break;
            case 3:
                goRight();
                break;
            case 4:
                goUp();
                break;
        }
    }

    public int randomMove() {
        return (int) ((Math.random() * (4 - 1)) + 1);
    }

}