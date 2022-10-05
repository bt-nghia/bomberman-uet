package uet.oop.bomberman.entities.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.Move;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.map.Map;
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
    private boolean rounded = false;
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
            // TODO : fix death when place 2 bombs at the same time
            TimerTask endGame = new TimerTask() {
                @Override
                public void run() {
                    System.exit(0);
                }
            };
            Timer timer = new Timer();
            if (countDeathUpdate > 0) {
                timer.schedule(timerTaskPlayerDeath, 100L);
                countDeathUpdate--;
            }
            timer.schedule(endGame, 1000L);
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
        for (int i = 1; i <= this.speed; ++i) {
            this.y -= 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y += 1;
                super.roundHorizontal();
                break;
            }
        }
//        BombermanGame.moveCamera(0, count);
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(
                Sprite.player_up,
                Sprite.player_up_1,
                Sprite.player_up_2,
                keepMoving, 30
        ).getFxImage());
    }

    @Override
    public void goDown() {
        for (int i = 1; i <= this.speed; ++i) {
            this.y += 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.y -= 1;
                super.roundHorizontal();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg((Sprite.movingSprite(
                Sprite.player_down,
                Sprite.player_down_1,
                Sprite.player_down_2,
                keepMoving, 30
        ).getFxImage()));
    }

    @Override
    public void goRight() {
        for (int i = 1; i <= this.speed; ++i) {
            if (canMoveCamera()) {
                BombermanGame.moveCamera(1, 0);
            }
            // special case
            if (this.x == 7 * Sprite.SCALED_SIZE) {
                BombermanGame.moveCamera(1, 0);
            }
            this.x += 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                if (canMoveCamera()) {
                    BombermanGame.moveCamera(-1, 0);
                }
                this.x -= 1;
                rounded = super.roundVertical();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(
                Sprite.player_right,
                Sprite.player_right_1,
                Sprite.player_right_2,
                keepMoving, 30
        ).getFxImage());
    }

    @Override
    public void goLeft() {
        int count = 0;
        for (int i = 1; i <= this.speed; ++i) {
            if (canMoveCamera()) {
                BombermanGame.moveCamera(-1, 0);
                count--;
            }
            // special case
            if (this.x == (BombermanGame.WIDTH - 8) * Sprite.SCALED_SIZE) {
                BombermanGame.moveCamera(-1, 0);
                count++;
            }
            this.x -= 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                if (canMoveCamera()) {
                    BombermanGame.moveCamera(1, 0);
                    count--;
                }
                this.x += 1;
                // intersect while moving horizontal -> round vertical to pass intersect
                rounded = super.roundVertical();
                break;
            }
        }
        keepMoving += this.speed;
        if (keepMoving > 100) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(
                Sprite.player_left,
                Sprite.player_left_1,
                Sprite.player_left_2,
                keepMoving, 30
        ).getFxImage());
//        System.out.println("count: " + count);
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
        if (keepMoving > 400) {
            keepMoving = 0;
        }
        setImg(Sprite.movingSprite(Sprite.player_dead3, Sprite.player_dead2, Sprite.player_dead3, keepMoving, 180).getFxImage());
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }

    public void removeBomb(Bomb bomb) {

    }

    public void plantTheBomb() {
        if (EntitySetManagement.bomberMan.bombList.size() < EntitySetManagement.bomberMan.numberOfBomb) {
            Bomb bomb = new Bomb(
                    this.getX() / Sprite.SCALED_SIZE,
                    this.getY() / Sprite.SCALED_SIZE,
                    Sprite.bomb_2.getFxImage());

            // place an obstacle in map
            // set != brick and wall
            Map.map2D[bomb.getY() / Sprite.SCALED_SIZE][bomb.getX() / Sprite.SCALED_SIZE] = '*';
            // check duplicate bomb
            boolean duplicate = false;
            for (Bomb bombExist : EntitySetManagement.bomberMan.bombList) {
                if (bombExist.intersect(bomb)) {
                    duplicate = true;
                }
            }
            try {
                // set time to explode
                if (!duplicate) {
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            bomb.setImg(Sprite.bomb_exploded2.getFxImage());
                            bomb.addFlameDFS();
                            bomb.setExplode(true);
                        }
                    };
                    EntitySetManagement.bomberMan.addBomb(bomb);
                    Timer timerEx = new Timer();
                    timerEx.schedule(timerTask, 3000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean canMoveCamera() {
        return this.x >= 7 * Sprite.SCALED_SIZE && this.x <= (BombermanGame.WIDTH - 8) * Sprite.SCALED_SIZE;
    }
}
