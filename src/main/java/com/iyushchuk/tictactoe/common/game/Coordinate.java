package com.iyushchuk.tictactoe.common.game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class Coordinate {

    @Getter
    private int x;

    @Getter
    private int y;

    public Coordinate toHumanCoordinates() {
        return new Coordinate(x + 1, y + 1);
    }

    public static Coordinate fromHumanCoordinates(int x, int y) {
        return new Coordinate(x - 1, y -1);
    }
}
