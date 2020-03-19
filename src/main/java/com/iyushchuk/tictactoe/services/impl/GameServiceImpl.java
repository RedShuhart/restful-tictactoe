package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.dto.BoardDto;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.exceptions.GameDoesNotExistException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.common.util.GridHelper;
import com.iyushchuk.tictactoe.domain.entities.Board;
import com.iyushchuk.tictactoe.domain.entities.Game;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.domain.repositories.GameRepository;
import com.iyushchuk.tictactoe.services.BoardService;
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.converters.BoardConverter;
import com.iyushchuk.tictactoe.services.converters.GameConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final BoardService boardService;
    private final GameConverter converter;

    public GameServiceImpl(
            GameRepository gameRepository,
            PlayerService playerService,
            BoardService boardService,
            GameConverter converter
    ) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.boardService = boardService;
        this.converter = converter;
    }

    @Override
    public List<GameDto> getAll() {
        return converter.fromEntities(gameRepository.findAll());
    }

    @Override
    public GameDto getById(String tag) throws GameDoesNotExistException {
        Game game = findGameByTag(tag);

        GameDto dto = converter.fromEntity(game);

        enrichWithGrid(dto, game.getBoard().getPlacements());

        return dto;
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
    public GameDto update(String tag, GameDto dto) throws ApplicationException {

        Game game = findGameByTag(tag);

        Player xPlayer = playerService.findPlayerByTag(dto.getXPlayer());

        Player oPlayer = playerService.findPlayerByTag(dto.getOPlayer());

        game.setXPlayer(xPlayer);
        game.setOPlayer(oPlayer);
        game.setState(dto.getState());

        boardService.update(game.getId(), new BoardDto(dto.getPlacements()));

        Game updatedGame = gameRepository.save(game);

        GameDto updatedGameDto =  converter.fromEntity(updatedGame);
        enrichWithGrid(updatedGameDto, game.getBoard().getPlacements());

        return updatedGameDto;
    }

    @Override
    public void delete(String tag) throws GameDoesNotExistException {
        Game game = gameRepository.findGameByTag(tag)
                .orElseThrow(() -> new GameDoesNotExistException(tag));

        gameRepository.delete(game);
    }

    private void enrichWithGrid(GameDto dto, String placements) {
        dto.setPlacements(placements);
        dto.setFancyBoard(GridHelper.toFancyGrid(placements, 10));
    }

    public Game findGameByTag(String tag) throws GameDoesNotExistException {
        return gameRepository.findGameByTag(tag)
                .orElseThrow(() -> new GameDoesNotExistException(tag));
    }
}
