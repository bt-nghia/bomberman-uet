package uet.oop.bomberman.entities.items;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

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
        if (checkBoundBomber() && !isUsed) {
            Sound.playSound("itemcollect", 1500);
            Map.map2D[this.y / Sprite.SCALED_SIZE][this.x / Sprite.SCALED_SIZE] = ' ';
            Bomb.radius += 1;
            this.isUsed = true;
            this.isVisible = false;
            TimerTask upToDate = new TimerTask() {
                @Override
                public void run() {
                    Bomb.radius -= 1;
                }
            };
            Timer timer = new Timer();
            try {
                timer.schedule(upToDate, 10000);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
