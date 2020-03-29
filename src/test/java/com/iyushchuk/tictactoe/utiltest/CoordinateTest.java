package com.iyushchuk.tictactoe.utiltest;

import com.iyushchuk.tictactoe.common.game.Coordinate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateTest {


    @Test
    void toHumanCoordinatesTest() {
        Coordinate coordinate = new Coordinate(0, 0);

        Coordinate expectedCoordinate = new Coordinate(1, 1);

        Coordinate actualCoordinate = coordinate.toHumanCoordinates();

        assertEquals(expectedCoordinate, actualCoordinate);
    }

    @Test
    void fromHumanCoordinates() {
        Coordinate coordinate = new Coordinate(1, 1);

        Coordinate expectedCoordinate = new Coordinate(0, 0);

        Coordinate actualCoordinate = Coordinate.fromHumanCoordinates(coordinate);

        assertEquals(expectedCoordinate, actualCoordinate);
    }


}
