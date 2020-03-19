package com.iyushchuk.tictactoe.services;

import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.game.Coordinate;

public interface GamePlayerService {

    GameDto playGame(String playerTag, String gameTag, Coordinate move) throws ApplicationException;
}
