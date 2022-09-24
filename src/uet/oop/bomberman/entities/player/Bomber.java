package uet.oop.bomberman.entities.player;

import javafx.scene.image.Image;
//import jdk.tools.jlink.internal.Platform;
import uet.oop.bomberman.controller.PlayerController;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.player.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {

    private int speed = Sprite.SCALED_SIZE / 8;
    private boolean isAlive = true;
    private int keepMoving = 0;
    public List<Bomb> bombList = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (!this.isAlive) {
            setUpBomberDeath();
//            EntitySetManagement.clearAll();
//            try {
//                Thread.sleep(3000);
//                System.exit(0);
//            } catch (Exception e) {
//                System.out.println("thread not sleep");
//            }
        }
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    // TODO: lam tron de player di chuyen chinh xac vao 1 o
    @Override
    public void goUp() {
        PlayerController.up = 1;
        for (int i = 1; i <= this.speed; ++i) {
            this.y -= 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.up = -1;
                this.y += 1;
                super.roundHorizontal();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, keepMoving, 60).getFxImage());
    }

    @Override
    public void goDown() {
        PlayerController.up = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.y += 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.up = -1;
                this.y -= 1;
                super.roundHorizontal();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg((Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, keepMoving, 60).getFxImage()));
    }

    @Override
    public void goRight() {
        PlayerController.right = 1;
        for (int i = 1; i <= this.speed; ++i) {
            this.x += 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.x -= 1;
                PlayerController.right = -1;
                super.roundVertical();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, keepMoving, 60).getFxImage());
    }

    @Override
    public void goLeft() {
        PlayerController.right = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.x -= 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.right = -1;
                this.x += 1;
                // intersect while moving horizontal -> round vertical to pass intersect
                /**
                 * 1.
                 * #            |        * #
                 * #            |        * #
                 * #  <-(player)|  --->  * #    |
                 *              |        *    <--(player) // moving vertical to solve while intersect
                 * #            |        * #
                 */
                super.roundVertical();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, keepMoving, 60).getFxImage());
    }

    public boolean checkPortal() {
        // check if player go to portal or not
        if (EntitySetManagement.enemyList.size() > 0) {
            return false;
        }
        return this.intersect(EntitySetManagement.portal);
    }

    public boolean touchEnemiesOrFlame() {
        for (Enemy enemy : EntitySetManagement.enemyList) {
            if (this.intersect(enemy)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean checkBoundBomb() {
        for (Bomb bomb : bombList) {
            double diffX = this.getX() - bomb.getX();
            double diffY = this.getY() - bomb.getY();
            if (!(Math.abs(diffX) < Sprite.SCALED_SIZE && Math.abs(diffY) < Sprite.SCALED_SIZE)) {
                bomb.setPassOver(false);
            }
            if (bomb.passOver) {
                return false;
            }
            if (this.intersect(bomb)) {
                return true;
            }
        }
        return false;
    }

    public void setUpBomberDeath() {
        setImg(Sprite.player_dead3.getFxImage());
//        setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, keepMoving, 60).getFxImage());
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }

    public void removeBomb(Bomb bomb) {

    }
}
