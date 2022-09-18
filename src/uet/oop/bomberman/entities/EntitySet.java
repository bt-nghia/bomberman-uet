package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enmies.Enemy;
import uet.oop.bomberman.entities.map.Brick;
import uet.oop.bomberman.entities.map.Grass;
import uet.oop.bomberman.entities.map.Wall;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntitySet {

    public static List<Bomb> bombList = new ArrayList<>();
    public static List<Wall> wallList = new ArrayList<>();
    public static List<Grass> grassList = new ArrayList<>();
    public static List<Enemy> enemyList = new ArrayList<>();
    public static List<Brick> brickList = new ArrayList<>();
//    public static Bomber bomber = new Bomber(1, 1, Sprite.player_right.getFxImage());

    public static void removeEnemies() {
        enemyList.removeIf(enemy -> !enemy.isAlive());
    }

    public static void removeBrick() {
        brickList.removeIf(Brick::isBroken);
    }

    public static void removeBomb() {
        bombList.removeIf(Bomb::isVisible);
    }

    public static void clearAll() {
        enemyList.clear();
        brickList.clear();
        grassList.clear();
    }
}
