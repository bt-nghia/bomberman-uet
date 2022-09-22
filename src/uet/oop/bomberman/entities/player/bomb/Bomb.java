package uet.oop.bomberman.entities.player.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    private boolean exploded = false;
    private int flameLength = 1;
    public boolean passOver = true;
    public int timeToExplode = 0;
    public List<Flame> allFlame = new ArrayList<>();

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if(this.exploded) {
            this.setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, this.animate, Sprite.DEFAULT_SIZE).getFxImage());
            if(this.timeToExplode == 1) {
                this.timeToExplode++;
                this.addFlameDFS();
            }
        } else {
            if(this.timeToExplode == 0) {
                this.timeToExplode++;
                setTimeToExplode();
            }
//            this.setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, this.animate, Sprite.DEFAULT_SIZE).getFxImage());
            this.setImg(Sprite.bomb.getFxImage());
        }
    }

    public boolean exploded() {
        return this.exploded;
    }

    public void setExplode(boolean explode) {
        this.exploded = explode;
    }

    public void addFlameDFS() {
        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x/Sprite.SCALED_SIZE, this.y/Sprite.SCALED_SIZE + i,
                    Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, 60, Sprite.SCALED_SIZE).getFxImage());
//            if (flame.checkBoundWall() || flame.checkBoundBrick()) {
//                break;
//            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x/Sprite.SCALED_SIZE, this.y/Sprite.SCALED_SIZE - i,
                    Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1, Sprite.explosion_vertical2, 60, Sprite.SCALED_SIZE).getFxImage());
//            if (flame.checkBoundWall() || flame.checkBoundBrick()) {
//                break;
//            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x/Sprite.SCALED_SIZE + i, this.y/Sprite.SCALED_SIZE,
                    Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, 60, Sprite.SCALED_SIZE).getFxImage());
//            if (flame.checkBoundWall() || flame.checkBoundBrick()) {
//                break;
//            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= flameLength; i++) {
            Flame flame = new Flame(this.x/Sprite.SCALED_SIZE - i, this.y/Sprite.SCALED_SIZE,
                    Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, 60, Sprite.SCALED_SIZE).getFxImage());
//            if (flame.checkBoundWall() || flame.checkBoundBrick()) {
//                break;
//            }
            this.allFlame.add(flame);
        }
    }

    public List<Flame> getAllFlame() {
        System.out.println(allFlame.size());
        return allFlame;
    }

    public void setPassOver(boolean passOver) {
        this.passOver = passOver;
    }

    public void setTimeToExplode() {
        Bomb temp = this;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                temp.setExplode(true);
            }
        };
        if (this.exploded) {
            Timer timer = new Timer();
            timer.schedule(timerTask, 2000L);
        }

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                EntitySetManagement.removeBrick();
                EntitySetManagement.removeBomb();
                EntitySetManagement.removeEnemies();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask1, 2500L);

    }
}
