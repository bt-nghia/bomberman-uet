package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.sound.Sound;

public class BombItem extends Item {
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (checkBoundBomber()) {
//            Sound.play("Item");
            int bombNum = EntitySetManagement.bomberMan.getNumberOfBomb();
            EntitySetManagement.bomberMan.setNumberOfBomb(bombNum + 1);
            this.isVisible = false;
        }
    }
}
