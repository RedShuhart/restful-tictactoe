package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.TicTacToeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicTacToeServiceImpl implements TicTacToeService {

    private final PlayerService playerService;
    private final GameService gameService;

    public TicTacToeServiceImpl(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @Override
    public List<GameDto> findPlayersGames(String tag, List<GameState> states) throws PlayerDoesNotExistException {

        Player player = playerService.findPlayerByTag(tag);

        return gameService.getGamesForPlayer(player, states);

    }
}
