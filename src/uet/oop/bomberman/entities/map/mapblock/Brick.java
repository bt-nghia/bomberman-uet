package uet.oop.bomberman.entities.map.mapblock;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Brick extends Entity {
    private int keepMoving = 0;

    private boolean isBroken = false;

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }


    public void setDestroyedImg() {
        this.keepMoving++;
        this.setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, keepMoving, Sprite.SCALED_SIZE).getFxImage());
    }

    @Override
    public void update() {
        keepMoving++;
        if(keepMoving > 100) {
            keepMoving = 0;
        }
        if (isBroken) {
            // add item handle
            this.setImg(
                    Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded1,
                    Sprite.brick_exploded2,
                    keepMoving,
                    30).getFxImage());
        }
    }

    public void removeBroken() {
        EntitySetManagement.brickList.removeIf(Brick::isBroken);
    }
}
