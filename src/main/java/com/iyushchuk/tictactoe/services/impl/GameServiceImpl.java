package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.GameDoesNotExistException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Board;
import com.iyushchuk.tictactoe.domain.entities.Game;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.domain.repositories.GameRepository;
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.converters.GameConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final GameConverter converter;

    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService, GameConverter converter) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.converter = converter;
    }

    @Override
    public List<GameDto> getAll() {
        return converter.fromEntities(gameRepository.findAll());
    }

    @Override
    public GameDto getById(String tag) throws GameDoesNotExistException {
        return converter.fromEntity(findGameByTag(tag));
    }

    @Override
    public GameDto create(GameDto dto) throws PlayerDoesNotExistException {

        Player xPlayer = playerService.findPlayerByTag(dto.getXPlayer());

        Player oPlayer = playerService.findPlayerByTag(dto.getOPlayer());

        Game game = new Game();

        game.setOPlayer(oPlayer);
        game.setXPlayer(xPlayer);
        game.setBoard(new Board());

        return converter.fromEntity(gameRepository.save(game));
    }

    @Override
    public GameDto update(String tag, GameDto dto) throws GameDoesNotExistException, PlayerDoesNotExistException {

        Game game = findGameByTag(tag);

        Player xPlayer = playerService.findPlayerByTag(dto.getXPlayer());

        Player oPlayer = playerService.findPlayerByTag(dto.getOPlayer());

        game.setXPlayer(xPlayer);
        game.setOPlayer(oPlayer);

        Game updatedGame = gameRepository.save(game);

        return converter.fromEntity(updatedGame);
    }

    private Game findGameByTag(String tag) throws GameDoesNotExistException {
        return gameRepository.findGameByTag(tag)
                .orElseThrow(() -> new GameDoesNotExistException(tag));
    }

    @Override
    public void delete(String tag) throws GameDoesNotExistException {
        Game game = gameRepository.findGameByTag(tag)
                .orElseThrow(() -> new GameDoesNotExistException(tag));

        gameRepository.delete(game);
    }


}
