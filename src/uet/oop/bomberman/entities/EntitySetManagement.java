package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Grass;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class EntitySetManagement {

    public static List<Wall> wallList = new ArrayList<>();
    public static List<Grass> grassList = new ArrayList<>();
    public static List<Enemy> enemyList = new ArrayList<>();
    public static List<Brick> brickList = new ArrayList<>();
    public static List<Item> itemList = new ArrayList<>();
    public static Entity portal = null;
    public static Bomber bomberMan = new Bomber(1, 1, Sprite.player_right.getFxImage());

    public static void removeEnemies() {
        try {
            int size = enemyList.size();
            enemyList.removeIf(enemy -> !enemy.isAlive());
            int newSize = enemyList.size();
            BombermanGame.score += (size - newSize) * 10;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeBrick() {
        try {
            brickList.removeIf(Brick::isBroken);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeBomb() {
        try {
            EntitySetManagement.bomberMan.bombList.removeIf(Bomb::exploded);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeUsedItems() {
        try {
            EntitySetManagement.itemList.removeIf(Item::isUsed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void clearAll() {
        enemyList.clear();
        brickList.clear();
        grassList.clear();
        wallList.clear();
        itemList.clear();
        bomberMan = null;
        portal = null;
    }
}
