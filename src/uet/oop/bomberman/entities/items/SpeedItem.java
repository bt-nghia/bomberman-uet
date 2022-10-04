package uet.oop.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;

import java.util.Timer;
import java.util.TimerTask;

public class SpeedItem extends Item {

    public SpeedItem(int xUnit, int yUnit, Image img) {
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
        if(checkBoundBomber() && !isUsed) {
            Map.map2D[this.y/32][this.x/32] = ' ';
            System.out.println("speed - bomber");
            int speed = EntitySetManagement.bomberMan.getSpeed();
            EntitySetManagement.bomberMan.setSpeed(speed + 4);
            this.isUsed = true;
            this.isVisible = false;
            TimerTask upToDate = new TimerTask() {
                @Override
                public void run() {
                    int speed = EntitySetManagement.bomberMan.getSpeed();
                    EntitySetManagement.bomberMan.setSpeed(speed - 4);
                }
            };
            Timer timer = new Timer();
            timer.schedule(upToDate, 10000);
        }
    }
}
