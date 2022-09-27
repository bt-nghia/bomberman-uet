package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy {
    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeed(2);
    }

    @Override
    public void update() {
        keepMoving++;
        if(keepMoving > 1000) {
            keepMoving = 0;
        }
        this.generateRandomSpeed();
        if (isAlive()) {
            if (this.getSpeedX() == 0) {
                this.y += this.getSpeedY();
                if (checkBoundWall() || checkBoundBomb() || checkBoundBrick() || getY() % Sprite.SCALED_SIZE == 0) {
                    if (getY() % Sprite.SCALED_SIZE != 0) {
                        this.y -= this.getSpeedY();
                    }
                    this.generateRandomDirection();
                }
            } else {
                this.x += this.getSpeedX();
                if (checkBoundBrick() || checkBoundBomb() || checkBoundWall() || getX() % Sprite.SCALED_SIZE == 0) {
                    if (getX() % Sprite.SCALED_SIZE != 0) {
                        this.x -= this.getSpeedX();
                    }
                    this.generateRandomDirection();
                }
            }
        } else {
            this.setImg(Sprite.minvo_dead.getFxImage());
        }
        if (isAlive()) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2
                        , Sprite.minvo_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedX() < 0){
                this.img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2
                        , Sprite.minvo_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
            } else if (this.getSpeedY() > 0) {
                this.img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2
                        , Sprite.minvo_right3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2
                        , Sprite.minvo_left3, this.y, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.minvo_dead.getFxImage();
        }
    }
}