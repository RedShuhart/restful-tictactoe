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


    private GameDto gameState;

    private Coordinate move;

    public GameResolver(GameDto dto, Coordinate move) {
        this.gameState = dto;
        this.move = move;
    }

    public GameDto getNextState() throws ApplicationException {
        String placementsAfterMove = performMove();

        boolean moreMovesAvailable = moreMovesAvailable(placementsAfterMove);

        boolean win = checkForWin(stringToGrid(placementsAfterMove, GAME_SIZE), move);

        GameState nextState = resolveNextState(moreMovesAvailable, win);

        return gameState
                .withState(nextState)
                .withPlacements(placementsAfterMove);
    }

    private boolean moreMovesAvailable(String placements) {
        return placements.contains("-");
    }

    private boolean checkForWin(char[][] grid, Coordinate coordinate) throws ApplicationException {
        char piece = getCurrentPiece();
        return Arrays.stream(TraversType.values()).anyMatch(traversType -> {
            List<Coordinate> visited = new ArrayList<>();
            return count(grid, coordinate, visited, traversType, piece) >= WINNING_CONDITION;
        });
    }

    private int count(char[][] grid, Coordinate coordinate, List<Coordinate> visited, TraversType traversType, char piece) {
        if (shouldProceed(grid, visited, coordinate, piece)) {
            visited.add(coordinate);
            return 1
                    + count(grid, Travers.nextCoordinate(coordinate, traversType), visited, traversType, piece)
                    + count(grid, Travers.oppositeCoordinate(coordinate, traversType), visited, traversType, piece);
        }
        return 0;
    }

    private boolean shouldProceed(char[][] grid, List<Coordinate> visited, Coordinate coordinate, char piece) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y] == piece && !visited.contains(coordinate);
    }

    private String performMove() throws ApplicationException {
        checkIfCanMove();
        int position = toOneDimIndex(move, GAME_SIZE);
        StringBuilder newPlacements = new StringBuilder(gameState.getPlacements());
        newPlacements.setCharAt(position, getCurrentPiece());
        return newPlacements.toString();
    }

    private void checkIfCanMove() throws FaultyMoveException, IllegalMoveException {
        if (isFaultyMove()) {
            throw new FaultyMoveException(move.toHumanCoordinates());
        }

        if (!canPerformMove()) {
            throw new IllegalMoveException(gameState.getState().getDescription());
        }
    }

    private boolean canPerformMove() {
        return !GameState.ENDGAME_STATES.contains(gameState.getState());
    }

    private boolean isFaultyMove() {
        int x = move.getX();
        int y = move.getY();
        return 0 <= x && x < GAME_SIZE && 0 <= y && y < GAME_SIZE && !isEmptySpot();
    }

    private boolean isEmptySpot() {
        return gameState.getPlacements().charAt(toOneDimIndex(move, GAME_SIZE)) == EMPTY;
    }

    private char getCurrentPiece() throws ApplicationException {
        return switch (gameState.getState()) {
            case X_MOVE -> X;
            case O_MOVE -> O;
            default -> throw new ApplicationException("Something went wrong");
        };
    }

    private GameState resolveNextState(boolean movesAvailable, boolean win) throws ApplicationException {
        if (movesAvailable && !win) {
            return switch (gameState.getState()) {
                case O_MOVE -> GameState.X_MOVE;
                case X_MOVE -> GameState.O_MOVE;
                default -> throw new ApplicationException("Something went wrong");
            };
        }

        if (win) {
            return switch (gameState.getState()) {
                case O_MOVE -> GameState.O_WON;
                case X_MOVE -> GameState.X_WON;
                default -> throw new ApplicationException("Something went wrong");
            };
        }

        return GameState.DRAW;
    }

}
