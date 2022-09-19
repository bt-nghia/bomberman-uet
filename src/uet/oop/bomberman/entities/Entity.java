package uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.entities.player.bomb.Bomb;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
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

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public boolean intersect(Entity other) {
        return other.getBoundary().intersects(this.getBoundary());
    }

    public boolean checkBoundBrick() {
        for(Brick brick : EntitySetManagement.brickList) {
            if(this.intersect(brick)) {return true;}
        }
        return false;
    }

    public boolean checkBoundWall() {
        for(Wall wall : EntitySetManagement.wallList) {
            if(this.intersect(wall)) {return true;}
        }
        return false;
    }

    public boolean checkBoundBomb() {
        for(Bomb bomb : Bomber.bombList) {
            if(this.intersect(bomb)) {return true;}
        }
        return false;
    }

    public void roundPosition(int directionUp, int directionRight) {
        int remainX = this.x % 32;
        int remainY = this.y % 32;
        switch (directionUp) {
            case 1:
                this.y-=remainY;
                break;
            case 0:
                this.y+=32-remainY;
                break;
            default:
                break;
        }
        switch (directionRight) {
            case 1:
                this.x+=32-remainX;
                break;
            case 0:
                this.x-=remainX;
                break;
            default:
                break;
        }
    }
}
