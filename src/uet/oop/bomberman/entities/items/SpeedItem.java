package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;

public class SpeedItem extends Item {

    public SpeedItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(this.intersect(EntitySetManagement.bomberMan)) {
            int speed = EntitySetManagement.bomberMan.getSpeed();
            EntitySetManagement.bomberMan.setSpeed(speed + 8);
        }
    }
}
