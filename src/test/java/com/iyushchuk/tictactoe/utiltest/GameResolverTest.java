package com.iyushchuk.tictactoe.utiltest;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.exceptions.FaultyMoveException;
import com.iyushchuk.tictactoe.common.exceptions.IllegalMoveException;
import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.common.game.GameResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameResolverTest {


    private static final String emptyPlacements = "----------------------------------------------------------------------------------------------------";

    private static final String placementsAfterXMoves = "X---------------------------------------------------------------------------------------------------";

    private static final String placementsAfterOMoves = "O---------------------------------------------------------------------------------------------------";

    private static final String xAlmostWinsPlacements = "XXXX------------------------------------------------------------------------------------------------";

    private static final String xWinsPlacements = "XXXXX-----------------------------------------------------------------------------------------------";

    private static final String oAlmostWinsPlacements = "OOOO------------------------------------------------------------------------------------------------";

    private static final String oWinsPlacements = "OOOOO-----------------------------------------------------------------------------------------------";

    private static final String nearDrawPlacements = "-OXOXOXOXOXOXOXOXOXOOXOXOXOXOXOXOXOXOXOXXOXOXOXOXOXOXOXOXOXOOXOXOXOXOXOXOXOXOXOXXOXOXOXOXOXOXOXOXOXO";

    private static final String drawPlacements = "XOXOXOXOXOXOXOXOXOXOOXOXOXOXOXOXOXOXOXOXXOXOXOXOXOXOXOXOXOXOOXOXOXOXOXOXOXOXOXOXXOXOXOXOXOXOXOXOXOXO";


    @Test
    void illegalMoveExpectedTest() {

        Assertions.assertThrows(IllegalMoveException.class, () -> {
            GameDto nextState = performMoveForTest(emptyPlacements, GameState.DRAW, new Coordinate(1, 1));
        });
    }

    @Test
    void faultyMoveExpectedTest() {

        Assertions.assertThrows(FaultyMoveException.class, () -> {
            GameDto nextState = performMoveForTest(emptyPlacements, GameState.X_MOVE, new Coordinate(110, 110));
        });
    }

    @Test
    void shouldResolveToXWonStateTest() throws ApplicationException {

        GameDto expectedState = GameDto.builder().placements(xWinsPlacements).state(GameState.X_WON).build();

        GameDto actualState = performMoveForTest(xAlmostWinsPlacements, GameState.X_MOVE, new Coordinate(0, 4));

        assertEquals(expectedState, actualState);
    }


    @Test
    void shouldResolveToOWonStateTest() throws ApplicationException {

        GameDto expectedState = GameDto.builder().placements(oWinsPlacements).state(GameState.O_WON).build();

        GameDto actualState = performMoveForTest(oAlmostWinsPlacements, GameState.O_MOVE, new Coordinate(0, 4));

        assertEquals(expectedState, actualState);
    }

    @Test
    void shouldResolveToDrawStateTest() throws ApplicationException {
        GameDto expectedState = GameDto.builder().placements(drawPlacements).state(GameState.DRAW).build();

        GameDto actualState = performMoveForTest(nearDrawPlacements, GameState.X_MOVE, new Coordinate(0, 0));

        assertEquals(expectedState, actualState);
    }

    @Test
    void shouldResolveToXMoveState() throws ApplicationException {
        GameDto expectedState = GameDto.builder().placements(placementsAfterOMoves).state(GameState.X_MOVE).build();

        GameDto actualState = performMoveForTest(emptyPlacements, GameState.O_MOVE, new Coordinate(0, 0));

        assertEquals(expectedState, actualState);
    }

    @Test
    void shouldResolveToOMoveState() throws ApplicationException {
        GameDto expectedState = GameDto.builder().placements(placementsAfterXMoves).state(GameState.O_MOVE).build();

        GameDto actualState = performMoveForTest(emptyPlacements, GameState.X_MOVE, new Coordinate(0, 0));

        assertEquals(expectedState, actualState);
    }

    private GameDto performMoveForTest(String initialPlacements, GameState initialState, Coordinate move) throws ApplicationException {
        GameDto game = GameDto.builder().placements(initialPlacements).state(initialState).build();

        GameResolver resolver = new GameResolver(game, move);

        return resolver.getNextState();
    }
}
