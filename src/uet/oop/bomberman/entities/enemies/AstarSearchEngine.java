package uet.oop.bomberman.entities.enemies;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;

import java.util.*;

public class AstarSearchEngine {

    private final static int ROW = 9;
    private final static int COL = 10;

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
        return grid[row][col] == ' ';
    }

    private static boolean isDestination(int row, int col, Pair<Integer, Integer> dest) {
        return row == dest.getKey() && col == dest.getValue();
    }

    private static double calculateHeuristicValue(int row, int col, Pair<Integer, Integer> dest) {
        return Math.sqrt(Math.abs(row - dest.getKey()) + Math.abs(col - dest.getValue()));
    }

    private static void tracePath(cell[][] cellDetails,Pair<Integer, Integer> src,  Pair<Integer, Integer> dest) {
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
        pathStack.push(src);
        while (!pathStack.isEmpty()) {
            Pair<Integer, Integer> node = pathStack.pop();
            System.out.println("(" + node.getKey() + "," + node.getValue() + ")");
        }
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
            // return isDest
            return true;
        }
        if(!closedList[i][j] && isUnBlocked(grid, i, j)) {
            gx = cellDetails[i][j].g + 1.0;
            hx = calculateHeuristicValue(i, j , dest);
            fx = hx + gx;

            // fx < f -> update
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

    public static void aStarSearch(char[][] grid, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {
        if(!isValid(src.getKey(), src.getValue())) {
            System.out.println("src invalid");
            return;
        }
        if(!isValid(dest.getKey(), dest.getValue())) {
            System.out.println("dest invalid");
            return;
        }
        if(!isUnBlocked(grid, dest.getKey(), dest.getValue()) || !isUnBlocked(grid, src.getKey(), src.getValue())) {
            System.out.println("src || dest blocked");
            return;
        }
        if(isDestination(src.getKey(), src.getValue(), dest)) {
            System.out.println("src = dest");
            return;
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
        tracePath(cellDetails,new Pair<>(src.getKey(), src.getValue()), dest);


        System.out.println("\n" + "cell is");
        for(int ix = 0; ix < ROW; ix++) {
            for(int jx = 0; jx < COL; jx++) {
                System.out.print("cell pos:" + ix + " " + jx + " ");
                System.out.println(cellDetails[ix][jx].toString());
            }
        }
    }


    public static void main(String[] args) {
        // for testing
        char[][] grid = {
                { ' ', '*', ' ', ' ', ' ', ' ', '*', ' ', ' ', ' ' },
                { ' ', ' ', ' ', '*', ' ', ' ', ' ', '*', ' ', ' ' },
                { ' ', ' ', ' ', '*', ' ', ' ', '*', ' ', '*', ' ' },
                { '*', '*', ' ', '*', ' ', '*', '*', '*', '*', ' ' },
                { ' ', ' ', ' ', '*', ' ', ' ', ' ', '*', ' ', '*' },
                { ' ', '*', ' ', ' ', ' ', ' ', '*', ' ', '*', '*' },
                { ' ', '*', '*', '*', '*', ' ', '*', '*', '*', ' ' },
                { ' ', '*', ' ', ' ', ' ', ' ', '*', ' ', ' ', ' ' },
                { ' ', ' ', ' ', '*', '*', '*', ' ', '*', '*', ' ' }
        };
//        System.out.print(Double.MAX_VALUE < 2 ? "nho" : "lon");
        aStarSearch(grid, new Pair<>(8,0), new Pair<>(0, 0));
    }
}
