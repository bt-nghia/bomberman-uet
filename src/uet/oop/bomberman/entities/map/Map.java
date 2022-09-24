package uet.oop.bomberman.entities.map;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.EntitySetManagement;
import uet.oop.bomberman.entities.map.mapblock.Brick;
import uet.oop.bomberman.entities.map.mapblock.Grass;
import uet.oop.bomberman.entities.map.mapblock.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {
    public static char[][] createMapByLevel(int level) {
        try {
            String path = "res/levels/Level" + level + ".txt";
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine().trim();
            String[] str = line.split("\\s+");
            // str[0] -> level
            BombermanGame.HEIGHT = Integer.parseInt(str[1]);
            BombermanGame.WIDTH = Integer.parseInt(str[2]);
            char[][] map2D = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];

            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
                line = bufferedReader.readLine();
                for (int j = 0; j < BombermanGame.WIDTH; j++) {
                    map2D[i][j] = line.charAt(j);
                }
            }
            fillMapImage(map2D);
            fileReader.close();
            bufferedReader.close();
            return map2D;
        } catch (Exception e) {
            System.out.println("map invalid: " + e.getMessage());
            return null;
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

                // portal behind the brick->render before that brick
                switch (map2D[i][j]) {
                    case '*':
                        EntitySetManagement.brickList.add(new Brick(j, i, Sprite.brick.getFxImage()));
                        break;
                    // monster case ...
                    default:
                        break;
                }
            }
        }
    }
}
