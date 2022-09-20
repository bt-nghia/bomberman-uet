package uet.oop.bomberman.entities.player.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.entities.player.Bomber;

public class Flame extends Entity {
    private boolean isVisible = true;

    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    // brick can be destroyed by explosion
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
        for(Enemy enemy : EntitySetManagement.enemyList) {
            if(this.intersect(enemy));
            enemy.setAlive(false);
        }
    }

    private void checkBomber() {

    }
}
