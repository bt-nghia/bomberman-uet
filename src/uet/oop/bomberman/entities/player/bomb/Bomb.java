package uet.oop.bomberman.entities.player.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    private boolean isVisible = true;
    private boolean exploded = false;
    private int flameLength = 2;
    public boolean passOver = true;
    public int timeToExplode;

    private List<Flame> allFlame = new ArrayList<>();
    private List<Flame> flameUp = new ArrayList<>();
    private List<Flame> flameDown = new ArrayList<>();
    private List<Flame> flameLeft = new ArrayList<>();
    private List<Flame> flameRight = new ArrayList<>();


    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public void addFlameDFS() {
        for(int i = 1; i <= flameLength; i++) {
            if(checkBoundWall() || checkBoundBrick()) {
                break;
            }
            flameDown.add(new Flame(this.x, this.y + 1, Sprite.explosion_horizontal.getFxImage()));
        }

        for(int i = 1; i <= flameLength; i++) {
            if(checkBoundWall() || checkBoundBrick()) {
                break;
            }
            flameUp.add(new Flame(this.x, this.y - 1, Sprite.explosion_horizontal.getFxImage()));
        }

        for(int i = 1; i <= flameLength; i++) {
            if(checkBoundWall() || checkBoundBrick()) {
                break;
            }
            flameRight.add(new Flame(this.x + 1, this.y, Sprite.explosion_vertical.getFxImage()));
        }

        for(int i = 1; i <= flameLength; i++) {
            if(checkBoundWall() || checkBoundBrick()) {
                break;
            }
            flameLeft.add(new Flame(this.x - 1, this.y, Sprite.explosion_vertical.getFxImage()));
        }
    }

    public List<Flame> getAllFlame() {
        return allFlame;
    }

    public List<Flame> getFlameUp() {
        return flameUp;
    }

    public List<Flame> getFlameDown() {
        return flameDown;
    }

    public List<Flame> getFlameLeft() {
        return flameLeft;
    }

    public List<Flame> getFlameRight() {
        return flameRight;
    }

    public void setExplode(boolean isVisible) {
        this.isVisible = isVisible;
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
        if(this.isVisible) {
            Timer timer = new Timer();
            timer.schedule(timerTask, 2000L);
        }

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {

            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask1, 2500L);

    }
}
