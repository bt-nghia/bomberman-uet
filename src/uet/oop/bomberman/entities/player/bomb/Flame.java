package uet.oop.bomberman.entities.player.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class Flame extends Entity {

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        checkFlameBrick();
        destroyFlameEnemy();
        checkFlameWall();
        checkBomber();
    }

    public boolean checkFlameBrick() {
        for (Brick brick : EntitySetManagement.brickList) {
            if (this.intersect(brick)) {
                brick.setBroken(true);
                return true;
            }
        }
        return false;
    }

    public boolean checkFlameWall() {
        for (Wall wall : EntitySetManagement.wallList) {
            if (this.intersect(wall)) {
                return true;
            }
        }
        return false;
    }

    public void destroyFlameEnemy() {
        for (Enemy enemy : EntitySetManagement.enemyList) {
            if(this.intersect(enemy) || enemy.checkBoundBombExplosion()) {
                enemy.setAlive(false);
            }
        }
    }

    public void checkBomber() {
        if (this.intersect(EntitySetManagement.bomberMan)) {
            EntitySetManagement.bomberMan.setAlive(false);
        }
        for(Bomb bomb : EntitySetManagement.bomberMan.bombList) {
            if(bomb.intersect(EntitySetManagement.bomberMan)) {
                EntitySetManagement.bomberMan.setAlive(false);
            }
        }
    }
}
