package uet.oop.bomberman.entities.enemies;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.sound.Sound;

import java.util.Random;

public abstract class Enemy extends Entity {

    private int speed = 2;
    private int speedX = this.speed;
    private int speedY = 0;
    private boolean isAlive = true;
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;
    protected int keepMoving = 0;

    public int getSpeed() {
        return speed;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
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
        if(this.intersect(EntitySetManagement.bomberMan)) {
            EntitySetManagement.bomberMan.setAlive(false);
        }
    }

    @Override
    public void update() {
        if (!this.isAlive) {
           Sound.playSound("enemyDeath");
           Sound.stopSound("enemyDeath");
        }
        checkBomber();
    }
}
