package com.iyushchuk.tictactoe.common.exceptions;

public class GameDoesNotExistException extends ApplicationException {

    private static final String GAME_DOES_NOT_EXIST_MESSAGE = "Game with tag %s does not exist";

    public GameDoesNotExistException(String tag) {
        super(String.format(GAME_DOES_NOT_EXIST_MESSAGE, tag));
    }
}
