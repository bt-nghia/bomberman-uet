package uet.oop.bomberman.entities.map;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.enemies.Balloom;
import uet.oop.bomberman.entities.enemies.Minvo;
import uet.oop.bomberman.entities.enemies.Oneal;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Grass;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.entities.player.Bomber;
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
                    EntitySetManagement.wallList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else {
                    EntitySetManagement.grassList.add(new Grass(j, i, Sprite.grass.getFxImage()));
                }

                if (map2D[i][j] == 'x') {
                    // EntitySet.portal = new Portal(j, i, Sprite.portal.getFxImage());
                    // cover the portal
                    EntitySetManagement.brickList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                }

//                if (map2D[i][j] == '2') {
//                    EntitySetManagement.enemyList.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage()));
//                }
//                if(map2D[i][j] == '1') {
//                    EntitySetManagement.enemyList.add(new Balloom(j, i, Sprite.balloom_left1.getFxImage()));
//                }
                // portal behind the brick->render before that brick
                switch (map2D[i][j]) {
                    case 'p':
                        EntitySetManagement.bomberMan = new Bomber(j, i, Sprite.player_right.getFxImage());
                        break;
                    case '1':
                        EntitySetManagement.enemyList.add(new Balloom(j, i, Sprite.balloom_left3.getFxImage()));
                        break;
                    case '2':
                        EntitySetManagement.enemyList.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                        break;
                    case '3':
                        EntitySetManagement.enemyList.add(new Minvo(j, i, Sprite.minvo_right1.getFxImage()));
                        break;
                    case '#':
                        EntitySetManagement.wallList.add(new Wall(j, i, Sprite.wall.getFxImage()));
                        break;
                    case '*':
                        EntitySetManagement.brickList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    case '9':
                        EntitySetManagement.brickList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        EntitySetManagement.itemList.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        createMapByLevel(2);
        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < 31; j++) {
                System.out.print(map2D[i][j]);
            }
            System.out.println();
        }
    }
}
