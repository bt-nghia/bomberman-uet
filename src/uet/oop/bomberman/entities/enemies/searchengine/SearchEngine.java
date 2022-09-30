package uet.oop.bomberman.entities.enemies.searchengine;

import javafx.util.Pair;

import java.util.Stack;

public abstract class SearchEngine {
    public final static int ROW = 15;
    public final static int COL = 29;

    public static class cell {
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

    public static boolean isValid(int row, int col) {
        return row >= 0 && row < ROW && col >= 0 && col < COL;
    }

    public static boolean isUnBlocked(char[][] grid, int row, int col) {
        return grid[row][col] != '6' && grid[row][col] != '8';
    }

    public static boolean isDestination(int row, int col, Pair<Integer, Integer> dest) {
        return row == dest.getKey() && col == dest.getValue();
    }

    public static double calculateHeuristicValue(int row, int col, Pair<Integer, Integer> dest) {
        return Math.sqrt(Math.pow(row - dest.getKey(), 2) + Math.pow(col - dest.getValue(), 2));
    }

    public static Pair<Integer, Integer> tracePath(AStar.cell[][] cellDetails, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {
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
        if (!pathStack.isEmpty()) {
            Pair<Integer, Integer> node = pathStack.pop();
            return node;
        }
//        for (int i = 0; i < ROW; i++) {
//            for (int j = 0; j < COL; j++) {
//                System.out.println(i + " " + j + " " + cellDetails[i][j].toString());
//            }
//        }
        return src;
    }
}
