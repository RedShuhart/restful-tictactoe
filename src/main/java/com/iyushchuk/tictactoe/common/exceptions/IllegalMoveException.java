package com.iyushchuk.tictactoe.common.exceptions;

public class IllegalMoveException extends ApplicationException {
    private static final String ILLEGAL_MOVE_MESSAGE = "Cannot perform move, reason: %s";

    public IllegalMoveException(String reason) {
        super(String.format(ILLEGAL_MOVE_MESSAGE, reason));
    }
}
