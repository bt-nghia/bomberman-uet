package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Flame extends Entity {
    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
}
