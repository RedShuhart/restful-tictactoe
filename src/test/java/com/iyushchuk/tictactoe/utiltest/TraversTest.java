package com.iyushchuk.tictactoe.utiltest;

import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.common.game.Travers;
import com.iyushchuk.tictactoe.common.game.TraversType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TraversTest {


    @Test
    void nextVerticalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(2, 1);

        Coordinate actualCoordinate = Travers.nextCoordinate(coordinate, TraversType.VERTICAL);

        assertEquals(expectedCoordinate, actualCoordinate);

    }

    @Test
    void nextHorizontalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(1, 2);

        Coordinate actualCoordinate = Travers.nextCoordinate(coordinate, TraversType.HORIZONTAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void nextRightDiagonalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(2, 2);

        Coordinate actualCoordinate = Travers.nextCoordinate(coordinate, TraversType.RIGHT_DIAGONAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void nextLeftDiagonalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(0, 2);

        Coordinate actualCoordinate = Travers.nextCoordinate(coordinate, TraversType.LEFT_DIAGONAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void oppositeVerticalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(0, 1);

        Coordinate actualCoordinate = Travers.oppositeCoordinate(coordinate, TraversType.VERTICAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void oppositeHorizontalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(1, 0);

        Coordinate actualCoordinate = Travers.oppositeCoordinate(coordinate, TraversType.HORIZONTAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void oppositeRightDiagonalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(0, 0);

        Coordinate actualCoordinate = Travers.oppositeCoordinate(coordinate, TraversType.RIGHT_DIAGONAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void oppositeLeftDiagonalCoordinateTest() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(2, 0);

        Coordinate actualCoordinate = Travers.oppositeCoordinate(coordinate, TraversType.LEFT_DIAGONAL);

        assertEquals(expectedCoordinate, actualCoordinate);
    }
}
