package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Move;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy implements Move {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        super.update();
        super.setSpeed(1);
        keepMoving++;
        if (keepMoving > 1000) {
            keepMoving = 0;
        }
        if (isAlive()) {
            randomMove();
            playImg();
        } else {
            this.setImg(Sprite.balloom_dead.getFxImage());
        }
    }

    public void playImg() {
        if (this.getSpeedX() > 0) {
            this.img = Sprite.movingSprite(
                    Sprite.balloom_right1,
                    Sprite.balloom_right2,
                    Sprite.balloom_right3,
                    this.x, Sprite.DEFAULT_SIZE
            ).getFxImage();
        } else if (this.getSpeedX() < 0) {
            this.img = Sprite.movingSprite(
                    Sprite.balloom_left1,
                    Sprite.balloom_left2,
                    Sprite.balloom_left3,
                    this.x, Sprite.DEFAULT_SIZE
            ).getFxImage();
        } else if (this.getSpeedY() > 0) {
            this.img = Sprite.movingSprite(
                    Sprite.balloom_right1,
                    Sprite.balloom_right2,
                    Sprite.balloom_right3,
                    this.y, Sprite.DEFAULT_SIZE
            ).getFxImage();
        } else {
            this.img = Sprite.movingSprite(
                    Sprite.balloom_left1,
                    Sprite.balloom_left2,
                    Sprite.balloom_left3,
                    this.y, Sprite.DEFAULT_SIZE
            ).getFxImage();
        }
    }

}
