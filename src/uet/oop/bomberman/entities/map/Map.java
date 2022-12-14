package uet.oop.bomberman.entities.map;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Balloom;
import uet.oop.bomberman.entities.enemies.Kondorian;
import uet.oop.bomberman.entities.enemies.Minvo;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Grass;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.entities.player.Bomber;
import uet.oop.bomberman.entities.portal.Portal;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {
    public static char[][] map2D = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];

    public static void createMapByLevel(int level) {
        try {
            String path = "res/levels/Level" + level + ".txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine().trim();
            String[] str = line.split("\\s+");
//             str[0] -> level
            BombermanGame.HEIGHT = Integer.parseInt(str[1]);
            BombermanGame.WIDTH = Integer.parseInt(str[2]);
            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < BombermanGame.WIDTH; j++) {
                    map2D[i][j] = line.charAt(j);
                }
            }
            fillMapImage(map2D);
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("map invalid: " + e.getMessage());
        }
    }

    public static void fillMapImage(char[][] map2D) {
        for (int i = 0; i < BombermanGame.HEIGHT; i++) {
            for (int j = 0; j < BombermanGame.WIDTH; j++) {
                if (i == 0 || j == 0 || i == BombermanGame.HEIGHT - 1 || j == BombermanGame.WIDTH - 1 || map2D[i][j] == '#') {
                    EntitySetManagement.getEntitySetManagement().getWallList().add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else {
                    EntitySetManagement.getEntitySetManagement().getGrassList().add(new Grass(j, i, Sprite.grass.getFxImage()));
                }
                switch (map2D[i][j]) {
                    case 'p':
                        EntitySetManagement.getEntitySetManagement().setBomberMan(new Bomber(j, i, Sprite.player_right.getFxImage()));
                        map2D[i][j] = ' ';
                        break;
                    case '1':
                        EntitySetManagement.getEntitySetManagement().getEnemyList().add(new Balloom(j, i, Sprite.balloom_left3.getFxImage()));
                        break;
                    case '2':
                        EntitySetManagement.getEntitySetManagement().getEnemyList().add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    case '3':
                        EntitySetManagement.getEntitySetManagement().getEnemyList().add(new Minvo(j, i, Sprite.minvo_right1.getFxImage()));
                        break;
                    case '4':
                        EntitySetManagement.getEntitySetManagement().getEnemyList().add(new Kondorian(j, i, Sprite.kondoria_right1.getFxImage()));
                        break;
                    case '#':
                        EntitySetManagement.getEntitySetManagement().getWallList().add(new Wall(j, i, Sprite.wall.getFxImage()));
                        break;
                    case '*':
                        EntitySetManagement.getEntitySetManagement().getBrickList().add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    case 'b':
                        EntitySetManagement.getEntitySetManagement().getItemList().add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        EntitySetManagement.getEntitySetManagement().getBrickList().add(new Brick(j, i, Sprite.brick.getFxImage()));
                        // set map brick so A_star can work
                        map2D[i][j] = '*';
                        break;
                    case 'f':
                        EntitySetManagement.getEntitySetManagement().getItemList().add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        EntitySetManagement.getEntitySetManagement().getBrickList().add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map2D[i][j] = '*';
                        break;
                    case 's':
                        EntitySetManagement.getEntitySetManagement().getItemList().add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        EntitySetManagement.getEntitySetManagement().getBrickList().add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map2D[i][j] = '*';
                        break;
                    case 'x':
                        EntitySetManagement.getEntitySetManagement().setPortal(new Portal(j, i, Sprite.portal.getFxImage()));
                        EntitySetManagement.getEntitySetManagement().getBrickList().add(new Brick(j, i, Sprite.brick.getFxImage()));
                        map2D[i][j] = '*';
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        createMapByLevel(2);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                System.out.print(map2D[i][j]);
            }
            System.out.println();
        }
        System.out.println(map2D[5][16]);
    }
}
