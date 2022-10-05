package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;

public abstract class Item extends Entity {
    protected boolean isVisible = true;
    protected boolean isUsed = false;
    protected int count = 0;
    protected boolean safeTodelete = false;

    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public abstract void update();

    protected boolean checkBoundBomber() {
        return this.intersect(EntitySetManagement.bomberMan);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void removeItem() {
        try {
            EntitySetManagement.itemList.removeIf(Item::checkBoundBomber);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
