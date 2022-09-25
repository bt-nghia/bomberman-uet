package uet.oop.bomberman.entities.map.mapblock;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
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
        keepMoving++;
        setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, keepMoving, Sprite.SCALED_SIZE).getFxImage());
    }

    public void delayRemoveWall() {
        TimerTask time = new TimerTask() {
            @Override
            public void run() {
                setDestroyedImg();
            }
        };
        Timer timer = new Timer();
        timer.schedule(time, 0);
        this.isBroken = true;
    }

    @Override
    public void update() {
        if (this.isBroken) {
            setDestroyedImg();
        }
    }
}
