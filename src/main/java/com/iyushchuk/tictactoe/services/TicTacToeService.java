package com.iyushchuk.tictactoe.services;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;

import java.util.List;

public interface TicTacToeService {

    List<GameDto> findPlayersGames(String tag, List<GameState> states) throws PlayerDoesNotExistException;
}
