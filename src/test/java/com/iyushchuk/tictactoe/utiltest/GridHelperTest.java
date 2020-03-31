package com.iyushchuk.tictactoe.utiltest;

import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.common.util.GridHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GridHelperTest {

    private static final String correctGrid = RandomStringUtils.random(100, '-', 'X', 'O');

    private static final String invalidLongGrid = RandomStringUtils.random(101, '-', 'X', 'O');

    private static final String invalidShortGrid = RandomStringUtils.random(99, '-', 'X', 'O');

    private static final String invalidCharactersGrid = RandomStringUtils.random(100);

    private static final String samplePlacements = "-X-X-O-O----OXOX";

    private static char[][] sampleGrid = {
            {'-', 'X', '-', 'X'},
            {'-', 'O', '-', 'O'},
            {'-', '-', '-', '-'},
            {'O', 'X', 'O', 'X'}
    };

    @Test
    void shouldBeValidGridTest() {
        assertTrue(GridHelper.isValidGrid(correctGrid));
    }

    @Test
    void shouldBeInvalidCharactersGridTest() {
        assertFalse(GridHelper.isValidGrid(invalidCharactersGrid));
    }

    @Test
    void shouldBeInvalidTooShortGridTest() {
        assertFalse(GridHelper.isValidGrid(invalidShortGrid));
    }

    @Test
    void shouldBeInvalidTooLongGridTest() {
        assertFalse(GridHelper.isValidGrid(invalidLongGrid));
    }

    @Test
    void toOneDimensionIndexTest() {
        Coordinate coordinate = new Coordinate(5, 5);

        int expectedIndex = 55;

        int actualIndex = GridHelper.toOneDimIndex(coordinate, 10);

        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void stringToGridTest() {
        char[][] grid = GridHelper.stringToGrid(samplePlacements, 4);

        assertTrue(Arrays.deepEquals(sampleGrid, grid));
    }
}
