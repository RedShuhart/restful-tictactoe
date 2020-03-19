package com.iyushchuk.tictactoe.common.exceptions;

public class NotAuthorizedToPlayGameException extends ApplicationException {

    private static final String NOT_AUTHORIZED_TO_PLAY_MESSAGE = "Player with tag %s iz not authorized to play game with tag %s";

    public NotAuthorizedToPlayGameException(String playerTag, String gameTag) {
        super(String.format(NOT_AUTHORIZED_TO_PLAY_MESSAGE, playerTag, gameTag));
    }
}
