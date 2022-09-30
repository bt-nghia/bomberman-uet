package uet.oop.bomberman.entities.enemies.searchengine;

import javafx.util.Pair;

public class BFS extends SearchEngine {
    public static void bfs(char[][] grid, Pair<Integer, Integer> src, Pair<Integer, Integer> dest, Pair<Integer, Integer> parent) {
        if (src.equals(dest)) {

        }
    }

    public static Pair<Integer, Integer> search(char[][] grid, Pair<Integer, Integer> src, Pair<Integer, Integer> dest) {
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
        cell[][] cellDetails = new cell[ROW][COL];

        return new Pair<>(0, 0);
    }
}
