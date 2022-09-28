package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.entities.player.bomb.Bomb;

public class FlameItem extends Item {

    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(this.intersect(EntitySetManagement.bomberMan)) {
            int len = Bomber.flameLength;
            Bomber.flameLength = len + 1;
        }
    }
}
