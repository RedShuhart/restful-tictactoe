package com.iyushchuk.tictactoe.common.exceptions;

import lombok.Getter;

public class ApplicationException extends Exception {

    @Getter
    private final String message;

    public ApplicationException(String message) {
        super(message);
        this.message = message;
    }
}
