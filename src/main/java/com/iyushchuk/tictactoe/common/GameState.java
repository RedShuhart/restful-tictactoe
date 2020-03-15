package com.iyushchuk.tictactoe.common;

public enum GameState {

    X_MOVE ("Player X should move next"),
    O_MOVE("Player O should move next"),
    X_WON("Congratulations, player X won!"),
    O_WON("Congratulations, player O won!"),
    DRAW("Oh no, it's a draw!");

    private String description;

    private GameState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
