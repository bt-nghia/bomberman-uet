package uet.oop.bomberman.entities.enemies.searchengine;

import javafx.util.Pair;
import java.util.*;

public class SearchEngine {

    private final static int ROW = 13;
    private final static int COL = 31;

    static class cell {
        public int parentX = 0, parentY = 0;
        public double f, g, h;

        @Override
        public String toString() {
            return "cell{" +
                    "parentX=" + parentX +
                    ", parentY=" + parentY +
                    ", f=" + f +
                    ", g=" + g +
                    ", h=" + h +
                    '}';
        }
    }

    private static boolean isValid(int row, int col) {
        return row >= 0 && row < ROW && col >= 0 && col < COL;
    }

    private static boolean isUnBlocked(char[][] grid, int row, int col) {
        return grid[row][col] == ' ' || grid[row][col] == 'p';
    }

    private static boolean isDestination(int row, int col, Pair<Integer, Integer> dest) {
        return row == dest.getKey() && col == dest.getValue();
    }

    private static double calculateHeuristicValue(int row, int col, Pair<Integer, Integer> dest) {
        return Math.abs(row - dest.getKey()) + Math.abs(col - dest.getValue());
    }

    private static Pair<Integer, Integer> tracePath(cell[][] cellDetails, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {
        System.out.println("PATH: ");
        int row = dest.getKey();
        int col = dest.getValue();

        Stack<Pair<Integer, Integer>> pathStack = new Stack<>();
        while (cellDetails[row][col].parentX != src.getKey() || cellDetails[row][col].parentY != src.getValue()) {
            pathStack.add(new Pair<>(row, col));
            int tempRow = cellDetails[row][col].parentX;
            int tempCol = cellDetails[row][col].parentY;
            row = tempRow;
            col = tempCol;
        }
        pathStack.push(new Pair<>(row, col));
        Pair<Integer, Integer> node = pathStack.pop();
        System.out.println("(" + node.getKey() + "," + node.getValue() + ")");
        return node;
    }

    private static boolean pathProcessor(
            int pi, int pj,
            int i, int j ,
            Pair<Integer, Integer> dest,
            double fx, double gx, double hx,
            cell[][] cellDetails,
            boolean[][] closedList,
            char[][] grid,
            Set<Pair<Double, Pair<Integer, Integer>>> openList
    ) {
        if(!isValid(i, j)) {return false;}
        if(isDestination(i, j, dest)) {
            cellDetails[i][j].parentX = pi;
            cellDetails[i][j].parentY = pj;
            return true;
        }
        if(!closedList[i][j] && isUnBlocked(grid, i, j)) {
            gx = cellDetails[pi][pj].g + 1.0;
            hx = calculateHeuristicValue(i, j , dest);
            fx = hx + gx;

            if(cellDetails[i][j].f == Double.MAX_VALUE || cellDetails[i][j].f > fx) {
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
        if(!isValid(src.getKey(), src.getValue())) {
            System.out.println("src invalid");
            return src;
        }
        if(!isValid(dest.getKey(), dest.getValue())) {
            System.out.println("dest invalid");
            return src;
        }
        if(!isUnBlocked(grid, dest.getKey(), dest.getValue()) || !isUnBlocked(grid, src.getKey(), src.getValue())) {
            System.out.println("src || dest blocked");
            return src;
        }
        if(isDestination(src.getKey(), src.getValue(), dest)) {
            System.out.println("src = dest");
            return src;
        }
        boolean[][] closedList = new boolean[ROW][COL];
        cell[][] cellDetails = new cell[ROW][COL];

        // init cell details
        for(int i = 0; i < ROW; i++) {
            for(int j = 0; j < COL; j++) {
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

        Set<Pair<Double, Pair<Integer, Integer>>> openList = new HashSet<>();
        openList.add(new Pair<>(0D, new Pair<>(i, j)));

        boolean isDest = false;
        while(!openList.isEmpty()) {
            openList.stream().sorted();
            Pair<Double, Pair<Integer, Integer>> p = openList.iterator().next();
            openList.remove(openList.iterator().next());
            i = p.getValue().getKey();
            j = p.getValue().getValue();
            closedList[i][j] = true;
            double fx = 0, gx = 0, hx = 0;
            isDest = pathProcessor(i, j, i-1, j, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {break;}
            isDest = pathProcessor(i, j, i+1, j, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {break;}
            isDest = pathProcessor(i, j, i, j+1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {break;}
            isDest = pathProcessor(i, j, i, j-1, dest, fx, gx, hx, cellDetails, closedList, grid, openList);
            if (isDest) {break;}
        }
        System.out.println("trace:\n");
        return tracePath(cellDetails,new Pair<>(src.getKey(), src.getValue()), dest);
    }

    public static void bfsSearch(char[][] grid, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {

    }


//    public static void main(String[] args) {
//        int level = 1;
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
//            for(int i = 0; i < BombermanGame.HEIGHT; i++) {
//                for(int j = 0; j < BombermanGame.WIDTH; j++) {
//                        System.out.print(map2D[i][j]);
//                }
//                System.out.println();
//            }
//            System.out.println("src" + "(" + map2D[11][13]+ ")");
//            Pair<Integer, Integer> pair = aStarSearch(map2D, new Pair<>(5,7), new Pair<>(1, 2));
//            System.out.println("(" + pair.getKey() + "," + pair.getValue() + ")");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}