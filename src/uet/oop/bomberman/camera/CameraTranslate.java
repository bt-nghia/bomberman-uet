package uet.oop.bomberman.camera;

import uet.oop.bomberman.BombermanGame;

public class CameraTranslate {
    public static void moveCamera(int x, int y) {
        BombermanGame.gc.clearRect(0, 0, BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());
        BombermanGame.gc.translate(-x, -y);
        BombermanGame.CAMERA_X += -x;
        BombermanGame.CAMERA_Y += -y;
    }
}