package uet.oop.bomberman.entities.map.mapblock;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends MapBlock {
    private int keepMoving = 0;

    private boolean isBroken = false;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

    public void setDestroyedImg() {
        this.keepMoving++;
        if (keepMoving > 200) keepMoving = 0;
        if (keepMoving >= 0 && keepMoving < 10) {
            this.setImg(Sprite.brick_exploded.getFxImage());
        } else if (keepMoving >= 10 && keepMoving < 20) {
            this.setImg(Sprite.brick_exploded1.getFxImage());
        } else {
            this.setImg(Sprite.brick_exploded2.getFxImage());
        }
        Map.map2D[getY() / Sprite.SCALED_SIZE][getX() / Sprite.SCALED_SIZE] = ' ';
    }

    @Override
    public void update() {
        if (isBroken) {
            this.setDestroyedImg();
        }
    }

    public void removeBroken() {
        EntitySetManagement.getEntitySetManagement().getBrickList().removeIf(Brick::isBroken);
    }
}
