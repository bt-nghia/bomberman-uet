package uet.oop.bomberman.entities.enemies.searchengine;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar extends SearchEngine {

    private static boolean pathProcessor(
            int pi, int pj,
            int i, int j,
            Pair<Integer, Integer> dest,
            double fx, double gx, double hx,
            cell[][] cellDetails,
            boolean[][] closedList,
            char[][] grid,
            List<Pair<Double, Pair<Integer, Integer>>> openList
    ) {
        if (!isValid(i, j)) {
            return false;
        }
        if (isDestination(i, j, dest)) {
            cellDetails[i][j].parentX = pi;
            cellDetails[i][j].parentY = pj;
            return true;
        }
        if (!closedList[i][j] && isUnBlocked(grid, i, j)) {
            if (pi != i && pj != j) {
                gx = cellDetails[pi][pj].g + Math.sqrt(2);
            }
            gx = cellDetails[pi][pj].g + 1.0;
            hx = calculateHeuristicValue(i, j, dest);
            fx = hx + gx;

            if (cellDetails[i][j].f == Double.MAX_VALUE || cellDetails[i][j].f > fx) {
                openList.add(new Pair<>(fx, new Pair<>(i, j)));
                // update cell
                cellDetails[i][j].f = fx;
                cellDetails[i][j].h = hx;
                cellDetails[i][j].g = gx;
                cellDetails[i][j].parentY = pj;
                cellDetails[i][j].parentX = pi;
            }
        }
        return false;
    }

    public static Pair<Integer, Integer> aStarSearch(char[][] grid, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {
        if (!isValid(src.getKey(), src.getValue())) {
            return src;
        }
        if (!isValid(dest.getKey(), dest.getValue())) {
            return src;
        }
        if (!isUnBlocked(grid, dest.getKey(), dest.getValue()) || !isUnBlocked(grid, src.getKey(), src.getValue())) {
            return src;
        }
        if (isDestination(src.getKey(), src.getValue(), dest)) {
            return src;
        }
        boolean[][] closedList = new boolean[ROW][COL];
        cell[][] cellDetails = new cell[ROW][COL];

        // init cell details
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                cellDetails[i][j] = new cell();
                cellDetails[i][j].parentX = -1;
                cellDetails[i][j].parentY = -1;
                cellDetails[i][j].f = Double.MAX_VALUE;
                cellDetails[i][j].h = Double.MAX_VALUE;
                cellDetails[i][j].g = Double.MAX_VALUE;
            }
        }

        int i = src.getKey();
        int j = src.getValue();

        cellDetails[i][j].parentX = i;
        cellDetails[i][j].parentY = j;
        cellDetails[i][j].f = 0D;
        cellDetails[i][j].h = 0D;
        cellDetails[i][j].g = 0D;

        List<Pair<Double, Pair<Integer, Integer>>> openList = new ArrayList<>();
        openList.add(new Pair<>(0D, new Pair<>(i, j)));

        boolean isDest = false;
        while (!openList.isEmpty()) {
            Collections.sort(openList, (o1, o2) -> {
                if (o1.getKey() > o2.getKey()) {
                    return 1;
                } else if (o1.getKey().equals(o2.getKey())) {
                    return 0;
                } else {
                    return -1;
                }
            });
            Pair<Double, Pair<Integer, Integer>> p = openList.iterator().next();
            openList.remove(openList.iterator().next());
            i = p.getValue().getKey();
            j = p.getValue().getValue();
            closedList[i][j] = true;
            double fx = 0, gx = 0, hx = 0;
            isDest = pathProcessor(i, j, i - 1, j, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {
                break;
            }
            isDest = pathProcessor(i, j, i + 1, j, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {
                break;
            }
            isDest = pathProcessor(i, j, i, j + 1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {
                break;
            }
            isDest = pathProcessor(i, j, i, j - 1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {
                break;
            }
//            isDest = pathProcessor(i, j, i + 1, j - 1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
//            if (isDest) {
//                break;
//            }
//            isDest = pathProcessor(i, j, i - 1, j - 1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
//            if (isDest) {
//                break;
//            }
//            isDest = pathProcessor(i, j, i - 1, j + 1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
//            if (isDest) {
//                break;
//            }
//            isDest = pathProcessor(i, j, i + 1, j + 1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
//            if (isDest) {
//                break;
//            }
        }
//        System.out.println("trace:\n");
        return tracePath(cellDetails, new Pair<>(src.getKey(), src.getValue()), dest);
    }


    // for testing
//    public static void main(String[] args) {
//        int level = 2;
//        try {
//            String path = "res/levels/Level" + level + ".txt";
//            File file = new File(path);
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = bufferedReader.readLine().trim();
//            String[] str = line.split("\\s+");
//            // str[0] -> level
//            BombermanGame.HEIGHT = Integer.parseInt(str[1]);
//            BombermanGame.WIDTH = Integer.parseInt(str[2]);
//            char[][] map2D = new char[BombermanGame.HEIGHT][BombermanGame.WIDTH];
//
//            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
//                line = bufferedReader.readLine();
//                for (int j = 0; j < BombermanGame.WIDTH; j++) {
//                    map2D[i][j] = line.charAt(j);
//                }
//            }
//            for (int i = 0; i < BombermanGame.HEIGHT; i++) {
//                for (int j = 0; j < BombermanGame.WIDTH; j++) {
//                    System.out.print(map2D[i][j] + ",");
//                }
//                System.out.println();
//            }
////            System.out.println("src" + "(" + map2D[11][13] + ")");
//            aStarSearch(map2D, new Pair<>(5, 7), new Pair<>(11, 11));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
