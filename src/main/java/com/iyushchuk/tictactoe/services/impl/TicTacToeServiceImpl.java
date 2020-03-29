package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.TicTacToeService;
import com.iyushchuk.tictactoe.services.converters.GameConverter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TicTacToeServiceImpl implements TicTacToeService {

    private final PlayerService playerService;
    private final GameService gameService;
    private final GameConverter gameConverter;

    public TicTacToeServiceImpl(PlayerService playerService, GameService gameService, GameConverter gameConverter) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.gameConverter = gameConverter;
    }

    @Override
    public List<GameDto> findPlayersGames(String tag, List<GameState> states) throws PlayerDoesNotExistException {

        Player player = playerService.findPlayerByTag(tag);

        return gameService.getGamesForPlayer(player, states);

    }
}
