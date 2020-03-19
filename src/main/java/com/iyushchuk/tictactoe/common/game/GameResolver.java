package com.iyushchuk.tictactoe.common.game;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.exceptions.FaultyMoveException;
import com.iyushchuk.tictactoe.common.exceptions.IllegalMoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.iyushchuk.tictactoe.common.util.GridHelper.stringToGrid;
import static com.iyushchuk.tictactoe.common.util.GridHelper.toOneDimIndex;

public class GameResolver {

    private static final int WINNING_CONDITION = 5;
    private static final int GAME_SIZE = 10;
    private static final char X = 'X';
    private static final char O = 'O';
    private static final char EMPTY = '-';

    private GameState state;

    private String placements;

    private Coordinate move;

    public GameResolver(GameDto dto, Coordinate move) {
        this.state = dto.getState();
        this.placements = dto.getBoard();
        this.move = move;
    }

    public GameDto getNextState() throws ApplicationException {
        String placementsAfterMove = performMove();

        boolean moreMovesAvailable = moreMovesAvailable(placementsAfterMove);

        boolean win = checkForWin(stringToGrid(placementsAfterMove, GAME_SIZE), move);

        GameState nextState = resolveNextState(moreMovesAvailable, win);

        return GameDto.builder()
                .board(placementsAfterMove)
                .state(nextState)
                .build();
    }

    private boolean moreMovesAvailable(String placements) {
        return placements.contains("-");
    }

    private boolean checkForWin(char[][] grid, Coordinate coordinate) {
        return Arrays.stream(TraversType.values()).anyMatch(traversType -> {
            List<Coordinate> visited = new ArrayList<>();
            return count(grid, coordinate, visited, traversType) >= WINNING_CONDITION;
        });
    }

    private int count(char[][] grid, Coordinate coordinate, List<Coordinate> visited, TraversType traversType) {
        if (shouldProceed(grid, visited, coordinate)) {
            visited.add(coordinate);
            return 1
                    + count(grid, Travers.nextCoordinate(coordinate, traversType), visited, traversType)
                    + count(grid, Travers.oppositeCoordinate(coordinate, traversType), visited, traversType);
        }
        return 0;
    }

    private boolean shouldProceed(char[][] grid, List<Coordinate> visited, Coordinate coordinate) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == 'X' && !visited.contains(coordinate);
    }

    private String performMove() throws FaultyMoveException, IllegalMoveException {
        checkIfCanMove();
        int position = toOneDimIndex(move, GAME_SIZE);
        StringBuilder newPlacements = new StringBuilder(placements);
        newPlacements.setCharAt(position, getCurrentPiece());
        return newPlacements.toString();
    }

    private void checkIfCanMove() throws FaultyMoveException, IllegalMoveException {
        if (isFaultyMove()) {
            throw new FaultyMoveException(move.toHumanCoordinates());
        }

        if (!canPerformMove()) {
            throw new IllegalMoveException(state.getDescription());
        }
    }

    private boolean canPerformMove() {
        return GameState.ENDGAME_STATES.contains(state);
    }

    private boolean isFaultyMove() {
        int x = move.getX();
        int y = move.getY();
        return 0 <= x && x < GAME_SIZE && 0 <= y && y < GAME_SIZE && isEmptySpot();
    }

    private boolean isEmptySpot() {
        return placements.charAt(toOneDimIndex(move, GAME_SIZE)) == EMPTY;
    }

    private char getCurrentPiece() {
        return switch (state) {
            case X_MOVE -> X;
            case O_MOVE -> O;
            default -> X;
        };
    }

    private  GameState resolveNextState(boolean movesAvailable, boolean win) throws ApplicationException {
        if(movesAvailable && !win) {
            return switch (state) {
                case O_MOVE -> GameState.X_MOVE;
                case X_MOVE -> GameState.O_MOVE;
                default -> throw new ApplicationException("Something went wrong");
            };
        }

        if(win) {
            return switch (state) {
                case O_MOVE -> GameState.O_WON;
                case X_MOVE -> GameState.X_WON;
                default -> throw new ApplicationException("Something went wrong");
            };
        }

        return GameState.DRAW;
    }

}
