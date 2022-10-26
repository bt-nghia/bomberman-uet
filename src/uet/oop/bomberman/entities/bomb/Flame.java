package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {

    /*
    1 : up
    2 : down
    3 : right
    4 : left
    5 : up last
    6 : down last
    7 : right last
    8 : left last
     */
    public static final int F_up = 1;
    public static final int F_down = 2;
    public static final int F_right = 3;
    public static final int F_left = 4;
    public static final int F_upLast = 5;
    public static final int F_downLast = 6;
    public static final int F_rightLast = 7;
    public static final int F_leftLast = 8;

    private int direction = -1;

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public Flame(int xUnit, int yUnit, Image img, int direction) {
        super(xUnit, yUnit, img);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void update() {
        checkFlameBrick();
        destroyFlameEnemy();
        checkFlameWall();
        checkFlameBomber();
        checkFlameOtherBomb();

        switch (direction) {
            case 1:
            case 2:
                setImg(Sprite.movingSprite(
                        Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2,
                        Bomb.animation, Sprite.SCALED_SIZE * 3 / 2).getFxImage());
                break;
            case 3:
            case 4:
                setImg(Sprite.movingSprite(
                        Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2,
                        Bomb.animation, Sprite.SCALED_SIZE * 3 / 2).getFxImage());
                break;
            case 5:
                setImg(Sprite.movingSprite(
                        Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1,
                        Sprite.explosion_vertical_top_last2,
                        Bomb.animation, Sprite.SCALED_SIZE * 3 / 2).getFxImage());
                break;
            case 6:
                setImg(Sprite.movingSprite(
                        Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1,
                        Sprite.explosion_vertical_down_last2,
                        Bomb.animation, Sprite.SCALED_SIZE * 3 / 2).getFxImage());
                break;
            case 7:
                setImg(Sprite.movingSprite(
                        Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,
                        Sprite.explosion_horizontal_right_last2,
                        Bomb.animation, Sprite.SCALED_SIZE * 3 / 2).getFxImage());
                break;
            case 8:
                setImg(Sprite.movingSprite(
                        Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2,
                        Bomb.animation, Sprite.SCALED_SIZE * 3 / 2).getFxImage());
                break;
        }
    }

    public boolean checkFlameBrick() {
        for (Brick brick : EntitySetManagement.getEntitySetManagement().getBrickList()) {
            if (this.intersect(brick)) {
                brick.setBroken(true);
                return true;
            }
        }
        return false;
    }

    public boolean checkFlameWall() {
        for (Wall wall : EntitySetManagement.getEntitySetManagement().getWallList()) {
            if (this.intersect(wall)) {
                return true;
            }
        }
        return false;
    }

    public void destroyFlameEnemy() {
        for (Enemy enemy : EntitySetManagement.getEntitySetManagement().getEnemyList()) {
            if (this.intersect(enemy) || enemy.checkBoundBombExplosion()) {
                enemy.setAlive(false);
            }
        }
    }

    public void checkFlameBomber() {
        if (this.intersect(EntitySetManagement.getEntitySetManagement().getBomberMan())) {
            EntitySetManagement.getEntitySetManagement().getBomberMan().setAlive(false);
        }
        for (Bomb bomb : EntitySetManagement.getEntitySetManagement().getBomberMan().getBombList()) {
            if (bomb.intersect(EntitySetManagement.getEntitySetManagement().getBomberMan()) && bomb.exploded()) {
                EntitySetManagement.getEntitySetManagement().getBomberMan().setAlive(false);
            }
        }
    }

    public boolean checkFlameOtherBomb() {
        for (Bomb bomb : EntitySetManagement.getEntitySetManagement().getBomberMan().getBombList()) {
            if (bomb.intersect(this)) {
                bomb.setExploded(true);
                // max number of bomb is 2 so i can return now;
                return true;
            }
        }
        return false;
    }
}
