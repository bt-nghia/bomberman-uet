package uet.oop.bomberman.entities.player;

import javafx.scene.image.Image;
import uet.oop.bomberman.controller.PlayerController;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.player.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {

    private int numBombs = 1;
    private int flameLength = 1;
    private int speed = Sprite.SCALED_SIZE / 8;
    private boolean isAlive = true;
    private int keepMoving = 0;
    public static List<Bomb> bombList = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }


    @Override
    public void update() {

    }

    // TODO: lam tron de player di chuyen chinh xac vao 1 o
    public void goUp() {
        PlayerController.up = 1;
        for (int i = 1; i <= this.speed; ++i) {
            this.y -= 1;
            if(checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.up = -1;
                this.y+=1;
                break;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, keepMoving, 18).getFxImage());
    }

    public void goDown() {
        PlayerController.up = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.y += 1;
            if (checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.up = -1;
                this.y -= 1;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg((Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, keepMoving, 18).getFxImage()));
    }

    public void goRight() {
        PlayerController.right = 1;
        for (int i = 1; i <= this.speed; ++i) {
            this.x += 1;
            if(checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                this.x-=1;
                PlayerController.right = -11;
                break;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, keepMoving, 18).getFxImage());
    }

    public void goLeft() {
        PlayerController.right = 0;
        for (int i = 1; i <= this.speed; ++i) {
            this.x -= 1;
            if(checkBoundBomb() || checkBoundBrick() || checkBoundWall()) {
                PlayerController.right = -1;
                this.x+=1;
                break;
            }
        }
        keepMoving+=this.speed;
        if(keepMoving > 100) {keepMoving = 0;}
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, keepMoving, 18).getFxImage());
    }
}
