package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.sound.Sound;

public class SpeedItem extends Item {
    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (checkBoundBomber()) {
            Sound.play("Item");
            this.isVisible = false;
            EntityArr.bomberman.setSpeed(EntityArr.bomberman.getSpeed() + Sprite.SCALED_SIZE / 16);
        }
    }
}