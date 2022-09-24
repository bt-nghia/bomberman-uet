package uet.oop.bomberman.entities.player.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    private boolean isVisible = true;
//    private int keepTransforming = 0;

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        checkFlameBrick();
        destroyFlameEnemy();
        checkFlameWall();
        checkBomber();
//        keepTransforming++;
//        if(keepTransforming > 100) {keepTransforming = 0;}
//        setAnimate(keepTransforming);
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private boolean checkFlameBrick() {
        for (Brick brick : EntitySetManagement.brickList) {
            if (this.intersect(brick)) {
                this.isVisible = false;
                brick.setBroken(true);
                return true;
            }
        }
        return false;
    }

    private boolean checkFlameWall() {
        for (Wall wall : EntitySetManagement.wallList) {
            if (this.intersect(wall)) {
                this.isVisible = false;
                return true;
            }
        }
        return false;
    }

    private void destroyFlameEnemy() {
        for (Enemy enemy : EntitySetManagement.enemyList) {
            if(this.intersect(enemy) || enemy.checkBoundBombExplosion()) {
                enemy.setAlive(false);
            }
        }
    }

    private void checkBomber() {
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
