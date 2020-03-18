package com.iyushchuk.tictactoe.common.exceptions;

public class UnsupportedGridException extends ApplicationException {
    private static final String UNSUPPORTED_GRID_MESSAGE = "Provided grid is not supported";

    public UnsupportedGridException() {
        super(UNSUPPORTED_GRID_MESSAGE);
    }
}
