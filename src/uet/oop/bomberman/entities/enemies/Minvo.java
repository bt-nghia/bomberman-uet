package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move;
import uet.oop.bomberman.graphics.Sprite;

public class Minvo extends Enemy implements Move {
    public Minvo(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.setSpeed(2);
    }

    @Override
    public void update() {
        super.update();
        keepMoving++;
        if (keepMoving > 1000) {
            keepMoving = 0;
        }
        this.generateRandomSpeed();
        if (isAlive()) {
            randomMove();
            playImg();
        } else {
            this.setImg(Sprite.minvo_dead.getFxImage());
        }
    }

    public void playImg() {
        if (this.getSpeedX() > 0) {
            this.img = Sprite.movingSprite(
                    Sprite.minvo_right1,
                    Sprite.minvo_right2,
                    Sprite.minvo_right3,
                    this.x, Sprite.DEFAULT_SIZE
            ).getFxImage();
        } else if (this.getSpeedX() < 0) {
            this.img = Sprite.movingSprite(
                    Sprite.minvo_left1,
                    Sprite.minvo_left2,
                    Sprite.minvo_left3,
                    this.x, Sprite.DEFAULT_SIZE
            ).getFxImage();
        } else if (this.getSpeedY() > 0) {
            this.img = Sprite.movingSprite(
                    Sprite.minvo_right1,
                    Sprite.minvo_right2,
                    Sprite.minvo_right3,
                    this.y, Sprite.DEFAULT_SIZE
            ).getFxImage();
        } else {
            this.img = Sprite.movingSprite(
                    Sprite.minvo_left1,
                    Sprite.minvo_left2,
                    Sprite.minvo_left3,
                    this.y, Sprite.DEFAULT_SIZE
            ).getFxImage();
        }
    }

}