package uet.oop.bomberman.entities.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.controller.PlayerController;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.Move;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.map.Map;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomber extends Entity implements Move {
    private int speed = Sprite.SCALED_SIZE / 8;
    private boolean isAlive = true;
    private int keepMoving = 0;
    public List<Bomb> bombList = new ArrayList<>();
    private int numberOfBomb = 1;
    private int countDeathUpdate = 1;

    public static int flameLength = 1;

    public int getNumberOfBomb() {
        return numberOfBomb;
    }

    public void setNumberOfBomb(int numberOfBomb) {
        this.numberOfBomb = numberOfBomb;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (!this.isAlive) {
            TimerTask timerTaskPlayerDeath = new TimerTask() {
                @Override
                public void run() {
                    Sound.playSound("enemyDeath");
                    setUpBomberDeath();
                    Sound.stopSound("enemyDeath");
                }
            };
            // TODO : fix death when place 2 bombs
//            TimerTask endGame = new TimerTask() {
//                @Override
//                public void run() {
//                    System.exit(0);
//                }
//            };
            Timer timer = new Timer();
            if(countDeathUpdate > 0) {
                timer.schedule(timerTaskPlayerDeath, 100L);
                countDeathUpdate--;
            }
//            timer.schedule(endGame, 1000L);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, (x + 4), y);
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
        int count = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.y -= 1;
            count--;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.up = -1;
                this.y += 1;
                count++;
                super.roundHorizontal();
                break;
            }
        }
        BombermanGame.moveCamera(0, count);
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(
                Sprite.player_up,
                Sprite.player_up_1,
                Sprite.player_up_2,
                keepMoving, 54
        ).getFxImage());
    }

    @Override
    public void goDown() {
        int count = 0;
        PlayerController.up = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.y += 1;
            count++;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.up = -1;
                this.y -= 1;
                count--;
                super.roundHorizontal();
                break;
            }
        }
        BombermanGame.moveCamera(0, count);
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg((Sprite.movingSprite(
                Sprite.player_down,
                Sprite.player_down_1,
                Sprite.player_down_2,
                keepMoving, 54
        ).getFxImage()));
    }

    @Override
    public void goRight() {
        PlayerController.right = 1;
        int count = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.x += 1;
            count++;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.x -= 1;
                count--;
                PlayerController.right = -1;
                super.roundVertical();
                break;
            }
        }
        BombermanGame.moveCamera(count, 0);
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(
                Sprite.player_right,
                Sprite.player_right_1,
                Sprite.player_right_2,
                keepMoving, 54
        ).getFxImage());
    }

    @Override
    public void goLeft() {
        PlayerController.right = 0;
        int count = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.x -= 1;
            count--;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.right = -1;
                this.x += 1;
                count++;
                // intersect while moving horizontal -> round vertical to pass intersect
                super.roundVertical();
                break;
            }
        }
        BombermanGame.moveCamera(count, 0);
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(
                Sprite.player_left,
                Sprite.player_left_1,
                Sprite.player_left_2,
                keepMoving, 54
        ).getFxImage());
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
        keepMoving++;
        if(keepMoving > 400) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_dead3, Sprite.player_dead2, Sprite.player_dead3, keepMoving, 180).getFxImage());
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }

    public void removeBomb(Bomb bomb) {

    }

    public void plantTheBomb() {
        if(EntitySetManagement.bomberMan.bombList.size() < EntitySetManagement.bomberMan.numberOfBomb) {
            Bomb bomb = new Bomb(
                    this.getX() / Sprite.SCALED_SIZE,
                    this.getY() / Sprite.SCALED_SIZE,
                    Sprite.bomb_2.getFxImage());

            // place an obstacle in map
            // set != brick and wall
            Map.map2D[bomb.getY() / 32][bomb.getX() / 32] = '*';
            // check duplicate bomb
            boolean duplicate = false;
            for (Bomb bombExist : EntitySetManagement.bomberMan.bombList) {
                if (bombExist.intersect(bomb)) {
                    duplicate = true;
                }
            }
            try {
                // set time to explode
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        bomb.setImg(Sprite.bomb_exploded2.getFxImage());
                        bomb.addFlameDFS();
                        bomb.setExplode(true);
                    }
                };
                if (!duplicate) {
                    EntitySetManagement.bomberMan.addBomb(bomb);
                    Timer timerEx = new Timer();
                    timerEx.schedule(timerTask, 2000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
