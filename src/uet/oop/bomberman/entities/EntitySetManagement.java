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

    private List<Wall> wallList = new ArrayList<>();
    private List<Grass> grassList = new ArrayList<>();
    private List<Enemy> enemyList = new ArrayList<>();
    private List<Brick> brickList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();
    private Entity portal = null;
    private Bomber bomberMan = new Bomber(1, 1, Sprite.player_right.getFxImage());

    public void removeEnemies() {
        try {
            int size = enemyList.size();
            enemyList.removeIf(enemy -> !enemy.isAlive());
            int newSize = enemyList.size();
            BombermanGame.score += (size - newSize) * 10;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeBrick() {
        try {
            this.brickList.removeIf(Brick::isBroken);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeBomb() {
        try {
            this.bomberMan.getBombList().removeIf(Bomb::exploded);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeUsedItems() {
        try {
            this.itemList.removeIf(Item::isUsed);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearAll() {
        enemyList.clear();
        brickList.clear();
        grassList.clear();
        wallList.clear();
        itemList.clear();
        bomberMan = null;
        portal = null;
    }

    public List<Wall> getWallList() {
        return wallList;
    }

    public void setWallList(List<Wall> wallList) {
        this.wallList = wallList;
    }

    public List<Grass> getGrassList() {
        return grassList;
    }

    public void setGrassList(List<Grass> grassList) {
        this.grassList = grassList;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
    }

    public List<Brick> getBrickList() {
        return brickList;
    }

    public void setBrickList(List<Brick> brickList) {
        this.brickList = brickList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public Entity getPortal() {
        return portal;
    }

    public void setPortal(Entity portal) {
        this.portal = portal;
    }

    public Bomber getBomberMan() {
        return bomberMan;
    }

    public void setBomberMan(Bomber bomberMan) {
        this.bomberMan = bomberMan;
    }
}
