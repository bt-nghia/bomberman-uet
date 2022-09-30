package uet.oop.bomberman.entities.map.mapblock;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;

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
        if(keepMoving > 200) keepMoving = 0;
        if(keepMoving >= 0 && keepMoving < 10) {
            this.setImg(Sprite.brick_exploded.getFxImage());
        } else if(keepMoving >= 10 && keepMoving < 20) {
            this.setImg(Sprite.brick_exploded1.getFxImage());
        } else {
            this.setImg(Sprite.brick_exploded2.getFxImage());
        }
        Map.map2D[getY()/32][getX()/32] = ' ';
    }

    @Override
    public void update() {
        if (isBroken) {
            this.setDestroyedImg();
        }
    }

    public void removeBroken() {
        EntitySetManagement.brickList.removeIf(Brick::isBroken);
    }
}
