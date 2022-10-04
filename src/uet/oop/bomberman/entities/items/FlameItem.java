package uet.oop.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.player.Bomber;

import java.util.Timer;
import java.util.TimerTask;

public class FlameItem extends Item {

    public FlameItem(int xUnit, int yUnit, Image img) {
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
            System.out.println("flame - bomber");
            Bomber.flameLength+=1;
            this.isUsed = true;
            this.isVisible = false;
            TimerTask upToDate = new TimerTask() {
                @Override
                public void run() {
                    Bomber.flameLength-=1;
                }
            };
            Timer timer = new Timer();
            timer.schedule(upToDate, 10000);
        }
    }
}
