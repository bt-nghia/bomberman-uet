package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    public int animate;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        this.animate = 60;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setAnimate(int animate) {
        this.animate = animate;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public static boolean checkIntersectDeep(Rectangle2D a, Rectangle2D b) {
        if (b == null || a == null) return false;
        return a.getMaxX() > b.getMinX() && a.getMaxY() > b.getMinY() && a.getMinX() < b.getMaxX() && a.getMinY() < b.getMaxY();
    }

    public boolean intersect(Entity other) {
        return checkIntersectDeep(other.getBoundary(), this.getBoundary());
    }

    public boolean checkBoundBrick() {
        for (Brick brick : EntitySetManagement.brickList) {
            if (this.intersect(brick)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoundWall() {
        for (Wall wall : EntitySetManagement.wallList) {
            if (this.intersect(wall)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoundBomb() {
        for (Bomb bomb : EntitySetManagement.bomberMan.bombList) {
            if (this.intersect(bomb)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoundBombExplosion() {
        for (Bomb bomb : EntitySetManagement.bomberMan.bombList) {
            if (this.intersect(bomb) && bomb.exploded()) {
                return true;
            }
        }
        return false;
    }

    public void roundPosition(int directionUp, int directionRight) {
        int remainX = this.x % 32;
        int remainY = this.y % 32;
        switch (directionUp) {
            case 1:
                this.y -= (remainY);
                break;
            case 0:
                this.y += (32 - remainY);
                break;
            default:
                break;
        }
        switch (directionRight) {
            case 1:
                this.x += (32 - remainX);
                break;
            case 0:
                this.x -= remainX;
                break;
            default:
                break;
        }
    }

    public boolean roundVertical() {
        int oldY = this.y;
        if (this.y % Sprite.SCALED_SIZE > 2 * Sprite.SCALED_SIZE / 3) {
            this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
        } else if (this.y % Sprite.SCALED_SIZE < Sprite.SCALED_SIZE / 3) {
            this.y = Sprite.SCALED_SIZE * (this.y / Sprite.SCALED_SIZE);
        }
        int newY = this.y;
        return oldY != newY;
    }

    public boolean roundHorizontal() {
//        System.out.print(this.x + " ");
        int oldX = this.x;
        if (this.x % Sprite.SCALED_SIZE > 2 * Sprite.SCALED_SIZE / 3) {
            this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE) + Sprite.SCALED_SIZE;
        } else if (this.x % Sprite.SCALED_SIZE < Sprite.SCALED_SIZE / 3) {
            this.x = Sprite.SCALED_SIZE * (this.x / Sprite.SCALED_SIZE);
        }
//        System.out.println(this.x);
        int newX = this.x;
//        System.out.println(oldX + " " + newX);
//        System.out.println(newX - oldX);
        if (this.x >= 7 * 32 && this.x <= (BombermanGame.WIDTH - 8) * 32) {
            BombermanGame.moveCamera(newX - oldX, 0);
        }
        return oldX != newX;
    }

    public Rectangle getRect() {
        return new Rectangle(
                this.x * Sprite.SCALED_SIZE / 2,
                this.y * Sprite.SCALED_SIZE / 2,
                Sprite.SCALED_SIZE,
                Sprite.SCALED_SIZE);
    }
}
