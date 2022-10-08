package uet.oop.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class BombItem extends Item {
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (this.isVisible) {
            gc.drawImage(img, x, y);
        }
    }

    @Override
    public void update() {
        if (checkBoundBomber() && !isUsed) {
            Map.map2D[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] = ' ';
//            System.out.println("bomb - bomber");
            EntitySetManagement.bomberMan.setNumberOfBomb(2);
            this.isVisible = false;
            this.isUsed = true;
            TimerTask upToDate = new TimerTask() {
                @Override
                public void run() {
                    EntitySetManagement.bomberMan.setNumberOfBomb(1);
                }
            };
            Timer timer = new Timer();
            timer.schedule(upToDate, 10000);
        }
    }
}
