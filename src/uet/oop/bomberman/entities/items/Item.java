package uet.oop.bomberman.entities.items;

import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.player.Bomber;

import java.util.Iterator;

public abstract class Item extends Entity {
    protected boolean isVisible = false;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public abstract void update();

    protected boolean checkBoundBomber() {
        if(this.intersect(EntitySetManagement.bomberMan)) {
            return true;
        }
        return false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void removeItem() {
        EntitySetManagement.itemList.removeIf(Item::checkBoundBomber);
    }
}
