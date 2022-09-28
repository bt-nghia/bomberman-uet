package uet.oop.bomberman.entities.item;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntityArr;

import java.util.Iterator;

public abstract class Item extends Entity {
    protected boolean isVisible = false;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public abstract void update();

    protected boolean checkBoundBomber() {
        for (Bomber bomber : EntityArr.bombers) {
            if (this.intersects(bomber)) {
                return true;
            }
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
        Iterator<Item> itemIterator = EntityArr.items.listIterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.checkBoundBomber()) {
                itemIterator.remove();
            }
        }
    }
}