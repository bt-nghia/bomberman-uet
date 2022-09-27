package uet.oop.bomberman.entities.player.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    private int keepTransforming = 0;
    private boolean exploded = false;
    private int flameLength;
    public boolean passOver = true;
    public int timeToExplode = 0;
    public List<Flame> allFlame = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        flameLength = 1;
    }

    public void setFlameLength(int flameLength) {
        this.flameLength = flameLength;
    }


    public boolean exploded() {
        return this.exploded;
    }

    public void setExplode(boolean explode) {
        this.exploded = explode;
    }

    public int getKeepTransforming() {
        return keepTransforming;
    }

    public void setKeepTransforming(int keepTransforming) {
        this.keepTransforming = keepTransforming;
    }

    public void addFlameDFS() {
        int count = 0;

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE + i,
                    Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, Sprite.SCALED_SIZE).getFxImage());
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkBoundBrick()) {
                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE - i,
                    Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, animate, Sprite.SCALED_SIZE).getFxImage());
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkBoundBrick()) {
                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE + i, this.y / Sprite.SCALED_SIZE,
                    Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, Sprite.SCALED_SIZE).getFxImage());
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkBoundBrick()) {
                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE - i, this.y / Sprite.SCALED_SIZE,
                    Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, animate, Sprite.SCALED_SIZE).getFxImage());
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkBoundBrick()) {
                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }
    }

    public List<Flame> getAllFlame() {
        return allFlame;
    }

    public void setPassOver(boolean passOver) {
        this.passOver = passOver;
    }

    public void setTimeToExplode() {
        Bomb temp = this;

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                EntitySetManagement.removeBomb();
                EntitySetManagement.removeEnemies();
            }
        };

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                EntitySetManagement.removeBrick();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask1, 2500L);
        timer.schedule(timerTask2, 2700L);

    }

    @Override
    public void update() {
        keepTransforming++;
        if (keepTransforming > 100) {
            keepTransforming = 0;
        }
        if (this.exploded) {
            // remove obstacle in map 0 -> grass
            Map.map2D[this.getY()/32][this.getX()/32] = '0';
            this.setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, 90, Sprite.SCALED_SIZE).getFxImage());
            if (this.timeToExplode == 1) {
                this.timeToExplode++;
                this.addFlameDFS();
            }
        } else {
            if (this.timeToExplode == 0) {
                this.timeToExplode++;
                setTimeToExplode();
            }
            this.setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, keepTransforming, 90).getFxImage());
        }
    }
}
