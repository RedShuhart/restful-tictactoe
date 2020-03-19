package com.iyushchuk.tictactoe.common;

import lombok.Getter;

import java.util.List;

public enum GameState {

    X_MOVE ("Player X should move next!", "X player moves"),
    O_MOVE("Player O should move next!", "O player moves"),
    X_WON("Congratulations, player X won!", "X player won"),
    O_WON("Congratulations, player O won!", "O player won"),
    DRAW("Oh no, it's a draw!", "Draw");

    @Getter
    private String message;

    @Getter
    private String description;

    GameState(String message, String description) {

        this.description = description;
        this.message = message;
    }

    public static final List<GameState> ENDGAME_STATES = List.of(DRAW, X_WON, O_WON);

    public static final List<GameState> MOVE_STATES = List.of(X_MOVE, O_MOVE);

}
