package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.Move;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Random;

public abstract class Enemy extends Entity implements Move {

    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;
    protected int keepMoving = 0;
    protected int deathCount = 0;
    private int speed = 2;
    private int speedX = this.speed;
    private int speedY = 0;
    private boolean isAlive = true;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void generateRandomDirection() {
        Random rand = new Random();
        int direction = rand.nextInt(4);
        switch (direction) {
            case RIGHT:
                this.speedX = this.getSpeed();
                this.speedY = 0;
                break;
            case LEFT:
                this.speedX = this.getSpeed() * -1;
                this.speedY = 0;
                break;
            case UP:
                this.speedY = this.getSpeed();
                this.speedX = 0;
                break;
            case DOWN:
                this.speedY = this.getSpeed() * -1;
                this.speedX = 0;
                break;
        }
    }

    public void generateRandomSpeed() {
        Random rand = new Random();
        this.speed = rand.nextInt(3) + 2;
    }

    public void chaseBomber() {

    }

    public void checkBomber() {
        if (this.intersect(EntitySetManagement.getEntitySetManagement().getBomberMan())) {
            EntitySetManagement.getEntitySetManagement().getBomberMan().setAlive(false);
        }
    }

    public void randomMove() {
        if (this.getSpeedX() == 0) {
            this.y += this.getSpeedY();
            if (checkBoundWall() || checkBoundBomb() || checkBoundBrick() || getY() % Sprite.SCALED_SIZE == 0) {
                if (getY() % Sprite.SCALED_SIZE != 0) {
                    this.y -= this.getSpeedY();
                }
                this.generateRandomDirection();
            }
        } else {
            this.x += this.getSpeedX();
            if (checkBoundBrick() || checkBoundBomb() || checkBoundWall() || getX() % Sprite.SCALED_SIZE == 0) {
                if (getX() % Sprite.SCALED_SIZE != 0) {
                    this.x -= this.getSpeedX();
                }
                this.generateRandomDirection();
            }
        }
    }

    @Override
    public void update() {
        if (!this.isAlive) {
            if (deathCount == 0) {
                Sound.playSound("enemyDeath", 1500);
                deathCount = 1;
            }
        }
        checkBomber();
    }

    @Override
    public void goUp() {

    }

    @Override
    public void goDown() {

    }

    @Override
    public void goLeft() {

    }

    @Override
    public void goRight() {

    }
}
