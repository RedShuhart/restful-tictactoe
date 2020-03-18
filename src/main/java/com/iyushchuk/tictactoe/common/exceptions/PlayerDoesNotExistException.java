package com.iyushchuk.tictactoe.common.exceptions;

public class PlayerDoesNotExistException extends ApplicationException {

    private static final String PLAYER_DOES_NOT_EXIST_MESSAGE = "Player with tag %s does not exist";

    public PlayerDoesNotExistException(String tag) {
        super(String.format(PLAYER_DOES_NOT_EXIST_MESSAGE, tag));
    }
}
