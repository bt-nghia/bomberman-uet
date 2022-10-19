package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Entity {
    public static int radius = 1;
    public static int animation = 0;
    public boolean passOver = true;
    public int timeToExplode = 0;
    public List<Flame> allFlame = new ArrayList<>();
    private int bombNum = 1;
    private int keepTransforming = 0;
    private boolean exploded = false;

    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }


    public boolean exploded() {
        return this.exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public int getKeepTransforming() {
        return keepTransforming;
    }

    public void setKeepTransforming(int keepTransforming) {
        this.keepTransforming = keepTransforming;
    }

    public void addFlame() {
        int count = 0;

        for (int i = 1; i <= radius; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE + i,
                    (i == radius) ?
                            Sprite.movingSprite(
                                    Sprite.explosion_vertical_down_last,
                                    Sprite.explosion_vertical_down_last1,
                                    Sprite.explosion_vertical_down_last2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage() :
                            Sprite.movingSprite(
                                    Sprite.explosion_vertical,
                                    Sprite.explosion_vertical1,
                                    Sprite.explosion_vertical2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage(), (i == radius) ? Flame.F_downLast : Flame.F_down
            );
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkFlameBrick()) {
//                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }

        // up
        for (int i = 1; i <= radius; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE - i,
                    (i == radius) ?
                            Sprite.movingSprite(
                                    Sprite.explosion_vertical_top_last,
                                    Sprite.explosion_vertical_top_last1,
                                    Sprite.explosion_vertical_top_last2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage() :
                            Sprite.movingSprite(
                                    Sprite.explosion_vertical,
                                    Sprite.explosion_vertical1,
                                    Sprite.explosion_vertical2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage(), (i == radius) ? Flame.F_upLast : Flame.F_up
            );
            if (i == radius) {

            }
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkFlameBrick()) {
//                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= radius; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE + i, this.y / Sprite.SCALED_SIZE,
                    (i == radius) ?
                            Sprite.movingSprite(
                                    Sprite.explosion_horizontal_right_last,
                                    Sprite.explosion_horizontal_right_last1,
                                    Sprite.explosion_horizontal_right_last2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage() :
                            Sprite.movingSprite(
                                    Sprite.explosion_horizontal,
                                    Sprite.explosion_horizontal1,
                                    Sprite.explosion_horizontal2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage(), (i == radius) ? Flame.F_rightLast : Flame.F_right
            );
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkFlameBrick()) {
//                this.allFlame.add(flame);
                break;
            }
            this.allFlame.add(flame);
        }

        for (int i = 1; i <= radius; i++) {
            Flame flame = new Flame(this.x / Sprite.SCALED_SIZE - i, this.y / Sprite.SCALED_SIZE,
                    (i == radius) ?
                            Sprite.movingSprite(
                                    Sprite.explosion_horizontal_left_last,
                                    Sprite.explosion_horizontal_left_last1,
                                    Sprite.explosion_horizontal_left_last2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage() :
                            Sprite.movingSprite(
                                    Sprite.explosion_horizontal,
                                    Sprite.explosion_horizontal1,
                                    Sprite.explosion_horizontal2,
                                    animate, Sprite.SCALED_SIZE
                            ).getFxImage(), (i == radius) ? Flame.F_leftLast : Flame.F_left
            );
            if (flame.checkBoundWall()) {
                break;
            }
            if (flame.checkFlameBrick()) {
//                this.allFlame.add(flame);
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

        TimerTask soundTask = new TimerTask() {
            @Override
            public void run() {
                Sound.playSound("explosion", 1500);
            }
        };

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                EntitySetManagement.removeBomb();
            }
        };

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                EntitySetManagement.removeBrick();
                EntitySetManagement.removeEnemies();
            }
        };

        Timer timer = new Timer();
        try {
            timer.schedule(soundTask, 3010);
            timer.schedule(timerTask1, 3500L);
            timer.schedule(timerTask2, 3530L);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void update() {
        keepTransforming++;
        if (keepTransforming > 100) {
            keepTransforming = 0;
        }
        if (this.exploded) {
            // remove obstacle in map 0 -> grass
            Map.map2D[this.getY() / Sprite.SCALED_SIZE][this.getX() / Sprite.SCALED_SIZE] = ' ';
            this.setImg(
                    Sprite.movingSprite(
                            Sprite.bomb_exploded,
                            Sprite.bomb_exploded1,
                            Sprite.bomb_exploded2,
                            90, Sprite.SCALED_SIZE
                    ).getFxImage());
            if (this.timeToExplode == 1) {
                this.timeToExplode++;
                this.addFlame();
            }
        } else {
            if (this.timeToExplode == 0) {
                this.timeToExplode++;
                setTimeToExplode();
            }
            this.setImg(
                    Sprite.movingSprite(
                            Sprite.bomb,
                            Sprite.bomb_1,
                            Sprite.bomb_2,
                            keepTransforming, 90
                    ).getFxImage());
        }
        if (this.exploded) {
            if (animation >= Sprite.SCALED_SIZE * 3 / 2) {
                animation = 0;
            }
            animation++;
            setImg(Sprite.movingSprite(
                    Sprite.bomb_exploded,
                    Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2,
                    animation,
                    Sprite.SCALED_SIZE * 3 / 2
            ).getFxImage());
        }
    }
}
