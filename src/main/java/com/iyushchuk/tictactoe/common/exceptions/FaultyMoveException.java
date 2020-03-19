package com.iyushchuk.tictactoe.common.exceptions;

import com.iyushchuk.tictactoe.common.game.Coordinate;

public class FaultyMoveException extends ApplicationException {

    private static final String FAULTY_MOVE_MESSAGE = "Square (%d, %d) is not accessible";

    public FaultyMoveException(Coordinate coordinate) {
        super(String.format(FAULTY_MOVE_MESSAGE, coordinate.getX(), coordinate.getY()));
    }
}
