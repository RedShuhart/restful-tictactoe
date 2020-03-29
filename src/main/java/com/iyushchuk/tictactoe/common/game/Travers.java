package com.iyushchuk.tictactoe.common.game;

public class Travers {

    public static Coordinate nextCoordinate(Coordinate startCoordinate, TraversType traversType) {

        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        return switch (traversType) {
            case VERTICAL -> new Coordinate(x + 1, y);
            case HORIZONTAL -> new Coordinate(x, y + 1);
            case LEFT_DIAGONAL -> new Coordinate(x - 1, y + 1);
            case RIGHT_DIAGONAL -> new Coordinate(x + 1, y + 1);
        };
    }

    public static Coordinate oppositeCoordinate(Coordinate startCoordinate, TraversType traversType) {

        int x = startCoordinate.getX();
        int y = startCoordinate.getY();

        return switch (traversType) {
            case VERTICAL -> new Coordinate(x - 1, y);
            case HORIZONTAL -> new Coordinate(x, y - 1);
            case LEFT_DIAGONAL -> new Coordinate(x + 1, y - 1);
            case RIGHT_DIAGONAL -> new Coordinate(x - 1, y - 1);
        };
    }

    private Travers() {}

}