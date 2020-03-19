package com.iyushchuk.tictactoe.common.util;

import com.iyushchuk.tictactoe.common.game.Coordinate;

import java.util.stream.IntStream;

public class GridHelper {

    private static final String GRID_PATTERN = "^[-XO]{100}$";

    public static boolean isValidGrid(String grid) {
        return grid.matches(GRID_PATTERN);
    }

    public static char[][] stringToGrid(String placements, int gridSize) {
        return IntStream
                .iterate(0, i -> i + gridSize)
                .limit(placements.length() / gridSize)
                .mapToObj(i -> placements.substring(i, Math.min(i + gridSize, placements.length())))
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public static int toOneDimIndex(Coordinate coordinate, int gridSize) {
        return coordinate.getX() * gridSize + coordinate.getY();
    }

    public static String toFancyGrid(String placements, int gridSize) {
        char[][] grid = stringToGrid(placements, gridSize);
        int length = grid.length;
        StringBuilder builder = new StringBuilder();

        builder.append("\t");

        for (int i = 0; i < length; i++) {
            builder.append(i + 1).append("\t");
        }

        builder.append("\n");

        for (int i = 0; i < length; i++) {
            builder.append(i + 1).append("\t");
            for (int j = 0; j < length; j++) {
                builder.append(grid[i][j]).append("\t");
            }
            builder.append("\n");
        }

        builder.append("\t");

        for (int i = 0; i < length; i++) {
            builder.append(i + 1).append("\t");
        }

        return builder.toString();
    }
}

