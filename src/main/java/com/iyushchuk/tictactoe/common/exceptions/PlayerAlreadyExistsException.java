package com.iyushchuk.tictactoe.common.exceptions;

public class PlayerAlreadyExistsException extends ApplicationException {

    private static final String PLAYER_EXISTS_MESSAGE = "Player with tag %s already exists";

    public PlayerAlreadyExistsException(String tag) {
        super(String.format(PLAYER_EXISTS_MESSAGE, tag));
    }
}
